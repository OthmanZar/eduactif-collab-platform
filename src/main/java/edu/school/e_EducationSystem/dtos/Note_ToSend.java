package edu.school.e_EducationSystem.dtos;

import java.util.Date;

public record Note_ToSend(
        double value,
        String comment,
        String coursName,
        Integer idStudent,
        Integer idProf

) {
}
