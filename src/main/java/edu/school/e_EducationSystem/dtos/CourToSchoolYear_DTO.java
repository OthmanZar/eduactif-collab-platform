package edu.school.e_EducationSystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CourToSchoolYear_DTO(
        @NotEmpty(message = "Please Provide a list of courses")
        String[] courses,
        @NotBlank(message = "Please Provide an existent SchoolYear")
        String schoolName


) {
}
