package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Evaluation_ToReturn;
import edu.school.e_EducationSystem.dtos.Evaluation_ToSend;
import edu.school.e_EducationSystem.entities.Evaluation;
import edu.school.e_EducationSystem.entities.EvaluationID;
import edu.school.e_EducationSystem.entities.Project;
import edu.school.e_EducationSystem.entities.Users;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.mappers.EvaluationMapper;
import edu.school.e_EducationSystem.repositories.EvaluationRepository;
import edu.school.e_EducationSystem.repositories.ProjectRepository;
import edu.school.e_EducationSystem.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EvaluationServiceImpl implements IEvaluationService {

    private EvaluationRepository evaluationRepository;
    private EvaluationMapper evaluationMapper;
    private UsersRepository usersRepository;
    private ProjectRepository projectRepository;
    @Override
    public Evaluation_ToReturn addEvaluation(Evaluation_ToSend evaluation) throws UserException, ProjectException {

        Users users = usersRepository.findById(evaluation.id()).orElseThrow(
                ()->new UserException("User Not Found Exception"));

        Project project = projectRepository.findById(evaluation.projectName()).orElseThrow(
                () -> new ProjectException("Project Not Found"));
//        project.setName(evaluation.projectName());
        EvaluationID evaluationID = new EvaluationID();
        evaluationID.setProject(project);
        evaluationID.setUser(users);
        Evaluation evaluation1 = evaluationMapper.toSend(evaluation);
        evaluation1.setEvaluationID(evaluationID);


        return evaluationMapper.toReturn(evaluationRepository.save(evaluation1));
    }

    @Override
    public List<Evaluation_ToReturn> getAllEvaluationsOfProject(String name) throws ProjectException {

        Project project = projectRepository.findById(name).orElseThrow(
                () -> new ProjectException("Project Not Found"));

        List<Evaluation> projects =new ArrayList<>();
        projects = evaluationRepository.findAllByProject(project);
        if(projects.isEmpty()){
            throw new ProjectException("This Project Without any Evaluation");

        }


        return projects.stream().map(evaluation -> evaluationMapper.toReturn(evaluation)).toList();
    }

    @Override
    public double getTheMoyenneEvaluationsOfProject(String name) throws ProjectException {
        List<Evaluation_ToReturn> evaluation= getAllEvaluationsOfProject(name);
        if(evaluation.isEmpty()){
            throw new ProjectException("This Project Without any Evaluation");
        }
        List<Double> numbers = evaluation.stream().map(Evaluation_ToReturn::evaluation).toList();
        int num = numbers.size();
        double sum=0;
        for (Double number : numbers) {
            sum = sum + number;
        }


        return sum/num;
    }

    @Override
    public List<Evaluation_ToReturn> getAllEvaluationsOfProjectASC(String name) throws ProjectException {
        Project project = projectRepository.findById(name).orElseThrow(
                () -> new ProjectException("Project Not Found"));
        if(project.getEvaluations().isEmpty()){
            throw new ProjectException("This Project Without any Evaluation");
        }
        return evaluationRepository.findAllByProjectOrderByEvaluationAsc(project).
                stream().map(evaluation -> evaluationMapper.toReturn(evaluation)).
                toList();
    }

    @Override
    public List<Evaluation_ToReturn> getAllEvaluationsOfProjectADESC(String name) throws ProjectException {
        Project project = projectRepository.findById(name).orElseThrow(
                () -> new ProjectException("Project Not Found"));
        if(project.getEvaluations().isEmpty()){
            throw new ProjectException("This Project Without any Evaluation");
        }
        return evaluationRepository.findAllByProjectOrderByEvaluationDesc(project).
                stream().map(evaluation -> evaluationMapper.toReturn(evaluation)).
                toList();
    }
}
