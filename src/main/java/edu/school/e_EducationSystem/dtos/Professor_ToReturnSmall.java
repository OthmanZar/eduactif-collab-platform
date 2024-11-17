package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.enums.Sexe;

public record Professor_ToReturnSmall(
        Integer id,
        String fullName,
        String email,
        String imageName
) {
}
