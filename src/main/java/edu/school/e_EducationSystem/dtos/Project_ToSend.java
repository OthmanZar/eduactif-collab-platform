package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.Domaine;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public record Project_ToSend(

        String name,
        String description,
      //  String videoPath,
        Date date,
        Domaine domaine,
        boolean isVisible,
        @NonNull
        Integer studentId,
        @NonNull
        Integer professorId,
        boolean isOpen,
         MultipartFile video,
        List<MultipartFile> images,
        MultipartFile report

) {
}
