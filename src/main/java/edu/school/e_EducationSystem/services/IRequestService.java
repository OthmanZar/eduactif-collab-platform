package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Request_ToChange;
import edu.school.e_EducationSystem.dtos.Request_ToReturn;
import edu.school.e_EducationSystem.dtos.Request_ToSend;
import edu.school.e_EducationSystem.enums.State;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.RequestException;
import edu.school.e_EducationSystem.exceptions.UserException;

import java.util.List;

public interface IRequestService {

    Request_ToReturn addRequest(Request_ToSend requestToSend) throws UserException, ProjectException;
    Request_ToReturn changeStateOfRequest(Request_ToChange requestToChange) throws UserException, ProjectException, RequestException;
    List<Request_ToReturn> getRequestsOfAnUser(Integer userId) throws UserException;
    List<Request_ToReturn> getRequestsOfAProject(String projectName) throws ProjectException;
    List<Request_ToReturn> getAllRequest();
    List<Request_ToReturn> getAllRequestsWithAState(State state);

}
