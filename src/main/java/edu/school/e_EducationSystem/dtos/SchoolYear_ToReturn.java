package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.enums.Type;

public record SchoolYear_ToReturn (
        String name,
        Type type,
        int maxStudents,
        int maxCours,
        Departement departement

){
}
