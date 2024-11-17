package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.entities.Image;
import edu.school.e_EducationSystem.enums.Domaine;

import java.time.LocalDateTime;


public record Project_ToReturn  (
        String name,
        String description,
       // String videoPath,
        Domaine domaine,
        boolean isVisible,
        String studentFullName,
        String  professorFullName,
        boolean isOpen,
        Image image,
        LocalDateTime createdAt
//        double meanEvaluations,
//        int totalEvaluations,
//        int requestsTotal

){
}
