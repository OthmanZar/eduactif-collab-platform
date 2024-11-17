package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Project_ToReturn;
import edu.school.e_EducationSystem.dtos.Project_ToReturnTotal;
import edu.school.e_EducationSystem.dtos.Project_ToSend;
import edu.school.e_EducationSystem.dtos.Project_ToSendFinal;
import edu.school.e_EducationSystem.entities.Image;
import edu.school.e_EducationSystem.entities.Pdf;
import edu.school.e_EducationSystem.entities.Video;
import edu.school.e_EducationSystem.enums.Domaine;
import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.UserException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IProjectService {

    String addProject(Project_ToSend project) throws UserException, ProjectException, IOException, FilesException;
    Project_ToReturn getProjectByName(String name) throws ProjectException;
    List<Project_ToReturnTotal> getAllProjectsByStudent(Integer id) throws UserException;
    List<Project_ToReturnTotal> getAllProjectsByProfessor(Integer id);
    List<Project_ToReturn> getAllProjectsByDomaine(Domaine domaine);
    boolean deleteProject(String name) throws ProjectException;
    boolean changeVisibilityOfProject(String name , boolean isVisible) throws ProjectException;
    boolean changeIsOpenOfProject(String name , boolean isOpen) throws ProjectException;
    List<Project_ToReturn> getAllProjects();

    Project_ToReturnTotal getAllInformations(String name) throws ProjectException;

    boolean updateProject(Project_ToSend project) throws ProjectException, FilesException, IOException;
}
