package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Request_ToChange;
import edu.school.e_EducationSystem.dtos.Request_ToReturn;
import edu.school.e_EducationSystem.dtos.Request_ToSend;
import edu.school.e_EducationSystem.entities.Project;
import edu.school.e_EducationSystem.entities.Request;
import edu.school.e_EducationSystem.entities.RequestID;
import edu.school.e_EducationSystem.entities.Users;
import edu.school.e_EducationSystem.enums.State;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.RequestException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.mappers.RequestMapper;
import edu.school.e_EducationSystem.repositories.ProjectRepository;
import edu.school.e_EducationSystem.repositories.RequestRepository;
import edu.school.e_EducationSystem.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements IRequestService {

    private RequestRepository requestRepository;
    private UsersRepository usersRepository;
    private ProjectRepository projectRepository;
    private RequestMapper requestMapper;

    @Override
    public Request_ToReturn addRequest(Request_ToSend requestToSend) throws UserException, ProjectException {
        RequestID requestID = new RequestID();
        Users users = usersRepository.findById(requestToSend.userId()).orElseThrow(
                () -> new UserException("User Not Found"));

        Project project = projectRepository.findById(requestToSend.projectName()).orElseThrow(
                () -> new ProjectException("Project Not Found"));

        if( project.isOpen()){
            throw new ProjectException("This Project is Already Open");
        } else if (!project.isVisible()) {
            throw new ProjectException("This Project is not Visible");
        }
        if(requestToSend.userId().equals(project.getStudent().getId())
                || requestToSend.userId().equals(project.getProfessor().getId())){
            throw new ProjectException("As an Owner of The Project you can't do a request for you project");

        }


        requestID.setProject(project);
        requestID.setUser(users);

        Request request = requestMapper.toSend(requestToSend);
        request.setId(requestID);
        request.setProject(project);
        request.setUser(users);


        return requestMapper.toReturn(requestRepository.save(request));
    }

    @Transactional
    @Override
    public Request_ToReturn changeStateOfRequest(Request_ToChange requestToChange) throws UserException, ProjectException, RequestException {

        RequestID requestID = new RequestID();
        Users users = usersRepository.findById(requestToChange.idUser()).orElseThrow(
                () -> new UserException("User Not Found"));

        Project project = projectRepository.findById(requestToChange.projectName()).orElseThrow(
                () -> new ProjectException("Project Not Found"));

        requestID.setProject(project);
        requestID.setUser(users);

        Request request = requestRepository.findById(requestID).orElseThrow(() ->
                new RequestException("Request Not Found"));

        if(!Objects.equals(project.getStudent().getId(), requestToChange.idOwner())
                && !Objects.equals(project.getProfessor().getId(), requestToChange.idOwner())){
            throw new ProjectException("You Are Not the Owner Of this Project");
        }

        request.setState(requestToChange.state());
        return requestMapper.toReturn(request);
    }

    @Override
    public List<Request_ToReturn> getRequestsOfAnUser(Integer userId) throws UserException {
        Users users = usersRepository.findById(userId).orElseThrow(
                () -> new UserException("User Not Found"));


        return requestRepository.findAllByUser(users).
                stream().map(request -> requestMapper.toReturn(request)).toList();
    }

    @Override
    public List<Request_ToReturn> getRequestsOfAProject(String projectName) throws ProjectException {
        Project project = projectRepository.findById(projectName).orElseThrow(
                () -> new ProjectException("Project Not Found"));


        return requestRepository.findAllByProject(project).
                stream().map(request -> requestMapper.toReturn(request)).toList();
    }

    @Override
    public List<Request_ToReturn> getAllRequest() {

        return requestRepository.findAll().
                stream().map(request -> requestMapper.toReturn(request)).toList();
    }

    @Override
    public List<Request_ToReturn> getAllRequestsWithAState(State state) {


        return requestRepository.findAllByState(state).
                stream().map(request -> requestMapper.toReturn(request)).toList();
    }
}
