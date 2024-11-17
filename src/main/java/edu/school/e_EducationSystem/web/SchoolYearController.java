package edu.school.e_EducationSystem.web;

import edu.school.e_EducationSystem.dtos.CourToSchoolYear_DTO;
import edu.school.e_EducationSystem.dtos.Professor_ToReturn;
import edu.school.e_EducationSystem.dtos.SchoolYear_ToReturn;
import edu.school.e_EducationSystem.dtos.StudentDTO_ToReturnSmall;
import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.enums.Type;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.SchoolYearException;
import edu.school.e_EducationSystem.services.ISchoolYearService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class SchoolYearController {

    private ISchoolYearService schoolYearService;


    @PostMapping("/schoolYear")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> addSchoolYear(@RequestBody @Valid SchoolYear_ToReturn schoolYear)
            throws SchoolYearException {
        SchoolYear_ToReturn schoolYearToReturn= schoolYearService.addSchoolYear(schoolYear);

        return ResponseEntity.accepted().body(schoolYearToReturn.name());
    }

    //@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/infos/schoolYear/{name}")
    public ResponseEntity<SchoolYear_ToReturn> getSchoolYear(@PathVariable @Valid String name) throws SchoolYearException {
        SchoolYear_ToReturn schoolYearToReturn =  schoolYearService.getSchoolYearById(name);

       // null Problem
        return ResponseEntity.ok(schoolYearToReturn);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_PROFESSOR')")
    @GetMapping("/schoolYear/{name}/students")
    public ResponseEntity<List<StudentDTO_ToReturnSmall>> getAllStudents(@PathVariable @Valid String name) throws SchoolYearException {
        List<StudentDTO_ToReturnSmall> students = schoolYearService.getAllStudents(name);

        return ResponseEntity.ok(students);
    }

    //@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/infos/schoolYear/Department/{department}")
    public ResponseEntity<List<String>> getAllSchoolYearsByDepartment(@PathVariable(required = false) @Valid Departement department)
            throws SchoolYearException {
        List<String> students = schoolYearService.getAllSchoolYearsByDepartement(department);


        return ResponseEntity.ok(students);
    }

    //@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/infos/schoolYear/Type/{type}")
    public ResponseEntity<List<String>> getAllSchoolYearsByType(@PathVariable(required = false) @Valid Type type) throws SchoolYearException {


        List<String> students = schoolYearService.getAllSchoolYearsByType(type);

        return ResponseEntity.ok(students);
    }

    //@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/infos/schoolYear/{name}/courses")
    public ResponseEntity<List<String>> getAllCoursesOfSchoolYear(@PathVariable @Valid String name)
            throws SchoolYearException {

        List<String> students = schoolYearService.getAllCoursesOfSchoolYear(name);

        return ResponseEntity.ok(students);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_PROFESSOR')")
    @PostMapping("/schoolYear/cours")
    public ResponseEntity<Boolean> addCoursesToSchoolYear(@RequestBody @Valid CourToSchoolYear_DTO dto)
            throws SchoolYearException, CoursException {

        boolean b = schoolYearService.addCoursesToSchoolYear(dto);


        return ResponseEntity.accepted().body(b);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/schoolYear/{name}/professors")
    public ResponseEntity<Set<Professor_ToReturn>> getAllProfessors(@PathVariable @Valid String name)
            throws SchoolYearException {

        Set<Professor_ToReturn> students = schoolYearService.getAllProfessors(name);

        return ResponseEntity.ok(students);
    }

    @GetMapping("/infos/Departments")
    public ResponseEntity<List<String>> getAllDepartments()
             {



        return ResponseEntity.ok(Arrays.stream(Departement.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }


    @GetMapping("/infos/Types")
    public ResponseEntity<List<String>> getAllTypes()  {


        return ResponseEntity.ok(Arrays.stream(Type.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }



}
