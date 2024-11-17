package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.Sexe;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record StudentDTO_ToSend(
        String lastName,
        String firstName,
        String email,
        String phone,
        String password,
        Sexe sexe,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date birthday,
        String schoolYear,
        MultipartFile image
) {
}
