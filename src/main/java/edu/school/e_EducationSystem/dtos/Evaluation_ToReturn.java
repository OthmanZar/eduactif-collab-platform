package edu.school.e_EducationSystem.dtos;

import java.time.LocalDateTime;

public record Evaluation_ToReturn(
        double evaluation,
        String comment,
        String userName,
        LocalDateTime createdAt,
        Integer userId,
        String imageName
) {
}
