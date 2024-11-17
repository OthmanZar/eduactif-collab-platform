package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.Sexe;

import java.util.Date;

public record Admin_ToSend(

        String lastName,
        String firstName,
        String email,
        String phone,
        String password,
        Sexe sexe,
        Date birthday

) {
}
