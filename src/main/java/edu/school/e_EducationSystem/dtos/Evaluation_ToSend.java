package edu.school.e_EducationSystem.dtos;

import lombok.NonNull;

import java.util.Date;

public record Evaluation_ToSend(
        double evaluation,
        String comment,
        @NonNull
        String projectName,
        @NonNull
        Integer id


) {
}
