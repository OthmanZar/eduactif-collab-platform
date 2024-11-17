package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.CourToSchoolYear_DTO;
import edu.school.e_EducationSystem.dtos.Professor_ToReturn;
import edu.school.e_EducationSystem.dtos.SchoolYear_ToReturn;
import edu.school.e_EducationSystem.dtos.StudentDTO_ToReturnSmall;
import edu.school.e_EducationSystem.entities.SchoolYear;
import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.enums.Type;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.SchoolYearException;

import java.util.List;
import java.util.Set;

public interface ISchoolYearService {

    SchoolYear_ToReturn addSchoolYear(SchoolYear_ToReturn schoolYear) throws SchoolYearException;
    List<StudentDTO_ToReturnSmall> getAllStudents(String name) throws SchoolYearException;
    SchoolYear_ToReturn getSchoolYearById(String name) throws SchoolYearException;
    List<String> getAllSchoolYearsByDepartement(Departement departement) throws SchoolYearException;
    List<String> getAllSchoolYearsByType(Type type) throws SchoolYearException;
    List<String> getAllCoursesOfSchoolYear(String name) throws SchoolYearException;
    boolean addCoursesToSchoolYear(CourToSchoolYear_DTO dto) throws SchoolYearException, CoursException;
    Set<Professor_ToReturn> getAllProfessors(String name) throws SchoolYearException;
}
