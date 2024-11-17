package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.State;

import java.time.LocalDateTime;

public record Request_ToReturn(

        String projectName,
        String title,
        State state,
        String fullName,
        int userId,
        String purpose,
        LocalDateTime createdAt,
        String userType
) {
}
