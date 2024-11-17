package edu.school.e_EducationSystem.dtos;

import lombok.Builder;


@Builder
public record Tokens(

        String accessToken,
        String refreshToken
) {
}
