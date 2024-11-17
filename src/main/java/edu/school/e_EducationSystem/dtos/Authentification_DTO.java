package edu.school.e_EducationSystem.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;


public record Authentification_DTO(
        String userName,
        String password,
        boolean withRefreshToken,
        String granType,
        String refreshToken

) {
}
