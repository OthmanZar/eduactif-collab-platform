package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.Project_ToReturn;
import edu.school.e_EducationSystem.dtos.Project_ToReturnTotal;
import edu.school.e_EducationSystem.dtos.Project_ToSend;
import edu.school.e_EducationSystem.dtos.Project_ToSendFinal;
import edu.school.e_EducationSystem.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMapper {
    private final EvaluationMapper evaluationMapper;

    @Value("${upload.images.directory}")
    private String imagesUploadDirectory;

    @Value("${upload.videos.directory}")
    private String videosUploadDirectory;

    @Value("${upload.pdfs.directory}")
    private String pdfsUploadDirectory;
    public Project toSend(Project_ToSend project){
        Project project1 = new Project();

//        Student student = new Student();
//        student.setId(project.studentId());
//
//        Professor professor = new Professor();
//        professor.setId(project.professorId());

        project1.setName(project.name());
//        project1.setStudent(student);
//        project1.setDate(project.date());
        project1.setDomaine(project.domaine());
//        project1.setProfessor(professor);
        project1.setVisible(project.isVisible());
    //    project1.setVideoPath(project.videoPath());
        project1.setDescription(project.description());
        project1.setOpen(project.isOpen());


        return project1;

    }

    public Project_ToReturn toReturn(Project project){
        int evaluations=project.getEvaluations().size();
        Set<Image> images = project.getImages();
        List<Image> imageList = new ArrayList<>(images);

        return new Project_ToReturn(project.getName(),
                project.getDescription(),
               // project.getVideoPath(),
//                project.getDate(),
                project.getDomaine(), project.isVisible(),
                project.getStudent().getFirstName()+" "+project.getStudent().getLastName(),
                project.getProfessor().getFirstName()+" "+project.getProfessor().getLastName(),
                project.isOpen(),
                imageList.get(0),
                project.getCreatedAt()

                );
    }


    public Project_ToReturnTotal toReturnTotal(Project project){
        int evaluations=project.getEvaluations().size();

        return new Project_ToReturnTotal(
                project.getName(),
                project.getDescription(),
                project.getDomaine(),
                project.isVisible(),
                project.getStudent().getFirstName()+" "+project.getStudent().getLastName(),
                project.getProfessor().getFirstName()+" "+project.getProfessor().getLastName(),
                project.isOpen(),
                project.getImages(),
                project.getVideo(),
                project.getReport(),
                project.getEvaluations().stream().mapToDouble(Evaluation::getEvaluation).sum()/evaluations,
                evaluations,
                project.getRequests().size(),
                project.getEvaluations().stream().map(evaluationMapper::toReturn).collect(Collectors.toList()),
                project.getStudent().getId(),
                project.getProfessor().getId(),
                project.getProfessor().getProfileImage().getImageName(),
                project.getStudent().getProfileImage().getImageName()
        );
    }

}
