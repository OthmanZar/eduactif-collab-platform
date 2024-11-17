package edu.school.e_EducationSystem.dtos;

import java.util.Date;

public record Request_ToSend(
        Integer userId,
        String projectName,
        String purpose,
        String title


) {
}
