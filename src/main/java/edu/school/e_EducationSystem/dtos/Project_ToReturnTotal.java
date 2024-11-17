package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.entities.Image;
import edu.school.e_EducationSystem.entities.Pdf;
import edu.school.e_EducationSystem.entities.Video;
import edu.school.e_EducationSystem.enums.Domaine;

import java.util.List;
import java.util.Set;

public record Project_ToReturnTotal(

        String name,
        String description,
        // String videoPath,
        Domaine domaine,
        boolean isVisible,
        String studentFullName,
        String  professorFullName,
        boolean isOpen,
        Set<Image> image,
        Video video,
        Pdf report,
        double meanEvaluations,
        int totalEvaluations,
        int requestsTotal,
        List<Evaluation_ToReturn> evaluationToReturns,
        int studentId,
        int professorId,
        String professorImage,
        String studentImage
) {
}
