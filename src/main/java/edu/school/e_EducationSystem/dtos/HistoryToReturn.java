package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.SchoolState;
import edu.school.e_EducationSystem.enums.Type;

public record HistoryToReturn(
        String schoolYearName,
        Type type,
        SchoolState state,
        Integer date


) {
}
