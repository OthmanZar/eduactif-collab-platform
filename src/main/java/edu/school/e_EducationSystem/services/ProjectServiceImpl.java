package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Project_ToReturn;
import edu.school.e_EducationSystem.dtos.Project_ToReturnTotal;
import edu.school.e_EducationSystem.dtos.Project_ToSend;
import edu.school.e_EducationSystem.dtos.Project_ToSendFinal;
import edu.school.e_EducationSystem.entities.*;
import edu.school.e_EducationSystem.enums.Domaine;
import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.mappers.ProjectMapper;
import edu.school.e_EducationSystem.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository projectRepository;
    private final  ProjectMapper projectMapper;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final FilesService fileService;



    @Override
    public String addProject(Project_ToSend project) throws UserException, ProjectException, IOException, FilesException {


        Professor professor = professorRepository.findById(project.professorId()).orElseThrow(
                () -> new UserException("Professor Not Found"));
        Student student = studentRepository.findById(project.studentId()).orElseThrow(
                () -> new UserException("Student Not Found"));

        Project projectFinal = projectMapper.toSend(project);
        projectFinal.setStudent(student);
        projectFinal.setProfessor(professor);

        Set<Image> images = fileService.uploadImages(project.images());
        Pdf report = fileService.uploadReport(project.report());
        Video video = fileService.uploadVideo(project.video());

        projectFinal.setImages(images);
        projectFinal.setVideo(video);
        projectFinal.setReport(report);

        Project project1 = projectRepository.save(projectFinal);

        return project1.getName();
    }

    @Override
    public Project_ToReturn getProjectByName(String name) throws ProjectException {
        Project project = projectRepository.findById(name).orElseThrow(
                ()->new ProjectException("Project Not Found"));


        return projectMapper.toReturn(project);
    }

    @Override
    public List<Project_ToReturnTotal> getAllProjectsByStudent(Integer id) throws UserException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new UserException("Student Not Found"));



        List<Project> projects = null;
        try {
            projects = projectRepository.findAllByStudent(student);
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }


        return projects.stream().map(projectMapper::toReturnTotal).toList();
    }

    @Override
    public List<Project_ToReturnTotal> getAllProjectsByProfessor(Integer id) {
        Professor professor = new Professor();
        professor.setId(id);

        List<Project> projects = null;
        try {
            projects = projectRepository.findAllByProfessor(professor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return projects.stream().map(projectMapper::toReturnTotal).toList();
    }

    @Override
    public List<Project_ToReturn> getAllProjectsByDomaine(Domaine domaine) {

        List<Project> projects = projectRepository.findAllByDomaine(domaine);

        return projects.stream().map(project -> projectMapper.toReturn(project)).toList();
    }

    @Override
    public boolean deleteProject(String name) throws ProjectException {
        Project project = projectRepository.findById(name).orElseThrow(
                () -> new ProjectException("Project Not Found"));

        try {
            projectRepository.delete(project);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean changeVisibilityOfProject(String name, boolean isVisible) throws ProjectException {
        Project project = projectRepository.findById(name).orElseThrow(
                () -> new ProjectException("Project Not Found"));

        project.setVisible(isVisible);


        return true;
    }

    @Override
    public boolean changeIsOpenOfProject(String name, boolean isOpen) throws ProjectException {
        Project project = projectRepository.findById(name).orElseThrow(
                () -> new ProjectException("Project Not Found"));

        project.setOpen(isOpen);

        return true;
    }

    @Override
    public List<Project_ToReturn> getAllProjects() {
        List<Project> projects= projectRepository.findAll();
        return projects.stream().map(projectMapper::toReturn).toList();
    }


    @Override
    public Project_ToReturnTotal getAllInformations(String name) throws ProjectException {
        Project project = projectRepository.findById(name).orElseThrow(
                ()->new ProjectException("Project Not Found"));


        return projectMapper.toReturnTotal(project);

    }

    @Override
    public boolean updateProject(Project_ToSend project) throws ProjectException, FilesException, IOException {
        Project project1 = projectRepository.findById(project.name())
                .orElseThrow(() -> new ProjectException("Project Not Found"));

        project1.setDescription(project.description());
        project1.setOpen(project.isOpen());
        project1.setVisible(project.isVisible());
        project1.setDomaine(project.domaine());
        if(project.images()!=null){
            Set<Image> images = fileService.uploadImages(project.images());
            project1.setImages(images);
        }
        if (project.video()!=null){
            Video video = fileService.uploadVideo(project.video());
            project1.setVideo(video);
        }

        if (project.report()!=null){
            Pdf report = fileService.uploadReport(project.report());
            project1.setReport(report);
        }

       projectRepository.save(project1);

        return true;
    }


}
