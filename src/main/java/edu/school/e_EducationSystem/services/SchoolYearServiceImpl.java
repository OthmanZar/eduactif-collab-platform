package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.CourToSchoolYear_DTO;
import edu.school.e_EducationSystem.dtos.Professor_ToReturn;
import edu.school.e_EducationSystem.dtos.SchoolYear_ToReturn;
import edu.school.e_EducationSystem.dtos.StudentDTO_ToReturnSmall;
import edu.school.e_EducationSystem.entities.Cours;
import edu.school.e_EducationSystem.entities.SchoolYear;
import edu.school.e_EducationSystem.entities.Users;
import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.enums.Type;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.SchoolYearException;
import edu.school.e_EducationSystem.mappers.ProfessorMapper;
import edu.school.e_EducationSystem.mappers.SchoolYearMapper;
import edu.school.e_EducationSystem.mappers.StudentMapper;
import edu.school.e_EducationSystem.repositories.CoursRepository;
import edu.school.e_EducationSystem.repositories.SchoolYearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchoolYearServiceImpl implements ISchoolYearService {

    private final ProfessorMapper professorMapper;
    private final CoursRepository coursRepository;
    private SchoolYearRepository schoolYearRepository;
    private SchoolYearMapper schoolYearMapper;
    private StudentMapper studentMapper;
    @Override
    public SchoolYear_ToReturn addSchoolYear(SchoolYear_ToReturn schoolYear)  {

        return schoolYearMapper.toReturn(schoolYearRepository.save( schoolYearMapper.toSend(schoolYear)));
    }

    @Override
    public List<StudentDTO_ToReturnSmall> getAllStudents(String name) throws SchoolYearException {
        SchoolYear schoolYear = schoolYearRepository.findById(name).orElseThrow(
                ()->new SchoolYearException("School Year Not Found"));

        if(schoolYear.getStudents() == null){
            throw new SchoolYearException("No students in this SchoolYear");
        }
        return schoolYear.getStudents().stream().map(student -> studentMapper.toReturnSmall(student)).toList();
    }

    @Override
    public SchoolYear_ToReturn getSchoolYearById(String name) throws SchoolYearException {
        SchoolYear schoolYear = schoolYearRepository.findById(name).orElseThrow
                (()->new SchoolYearException("This SchoolYear Name Not Found"));

        return schoolYearMapper.toReturn(schoolYear) ;
    }

    @Override
    public List<String> getAllSchoolYearsByDepartement(Departement departement) throws SchoolYearException {
        List<SchoolYear> schoolYears;
        List<String> list = new ArrayList<>();

        schoolYears = schoolYearRepository.findAllByDepartement(departement);

        if(schoolYears == null){
            throw new SchoolYearException("No SchoolYear Found in this Department");
        }

        for (SchoolYear schoolYear : schoolYears){
            list.add(schoolYear.getName());
        }

        return list;
    }

    @Override
    public List<String> getAllSchoolYearsByType(Type type) throws SchoolYearException {
        List<SchoolYear> schoolYears;
        List<String> list = new ArrayList<>();
        schoolYears = schoolYearRepository.findAllByType(type);
        if (schoolYears == null){
            throw new SchoolYearException("No SchoolYear Found For this Type");
        }

        for (SchoolYear schoolYear : schoolYears){
            list.add(schoolYear.getName());
        }

        return list;
    }

    @Override
    public List<String> getAllCoursesOfSchoolYear(String name) throws SchoolYearException {

        SchoolYear schoolYear = schoolYearRepository.findById(name).orElseThrow(
                ()->new SchoolYearException("This SchoolYear Name Not Found"));

        return schoolYear.getCourses().stream().map(Cours::getCoursName)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean addCoursesToSchoolYear(CourToSchoolYear_DTO dto) throws SchoolYearException, CoursException {


        List<Cours> coursList = new ArrayList<>();
        for (String courseId : dto.courses()) {
            Cours cours = coursRepository.findById(courseId)
                    .orElseThrow(() -> new CoursException(courseId + " Not Found"));
            coursList.add(cours);
        }


        SchoolYear_ToReturn schoolYear = getSchoolYearById(dto.schoolName());
        if (getAllCoursesOfSchoolYear(dto.schoolName()).size() >= schoolYear.maxCours()) {
            throw new SchoolYearException("There are enough courses in this SchoolYear");
        }


        SchoolYear schoolYearEntity = schoolYearRepository.findById(dto.schoolName())
                .orElseThrow(() -> new SchoolYearException("SchoolYear Not Found"));


        List<Cours> existingCourses = schoolYearEntity.getCourses();
        for (Cours cours : coursList) {
            if (!existingCourses.contains(cours)) {
                existingCourses.add(cours);  // Add the new course
            } else {
                throw new SchoolYearException("The course " + cours.getCoursName() + " already exists in this SchoolYear");
            }
        }


        schoolYearRepository.save(schoolYearEntity);

        return true;
    }


    @Override
    public Set<Professor_ToReturn> getAllProfessors(String name) throws SchoolYearException {

        SchoolYear schoolYear = schoolYearRepository.findById(name).orElseThrow(
                ()->new SchoolYearException("This SchoolYear Name Not Found"));

        return  schoolYear.getCourses().stream().
                map(cours -> professorMapper.toReturn(cours.getProfessor())).collect(Collectors.toSet());
    }
}
