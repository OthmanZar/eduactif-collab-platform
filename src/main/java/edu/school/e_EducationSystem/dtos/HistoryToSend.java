package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.SchoolState;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record HistoryToSend(
        @NotNull(message = "Please provide Student Email")
        String studentEmail,
        @NotNull
        int date,
        @NotNull
        SchoolState state
) {
}
