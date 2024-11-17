package edu.school.e_EducationSystem.web;


import edu.school.e_EducationSystem.dtos.*;
import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.services.IProfessorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class ProfessorController {

    private IProfessorService professorService;



    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/professor/{id}/{newProfId}")
    public ResponseEntity<Boolean> deleteProfessor(@PathVariable @Valid Integer id ,@Valid @PathVariable Integer newProfId)
            throws UserException {

        professorService.deleteProfessor(id,newProfId);

        return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    @GetMapping("/professor/{id}/courses")
    public ResponseEntity<List<SchoolYearsAndCourses_DTO>> getAllCourses(@PathVariable @Valid Integer id) throws UserException {
        List<SchoolYearsAndCourses_DTO> cours = professorService.getAllCourses(id);


        return ResponseEntity.ok(cours);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    @GetMapping("/professor/{id}/schoolYears")
    public ResponseEntity<Set<String>> getAllSchoolYears(@PathVariable @Valid Integer id) throws UserException {
        Set<String> cours = professorService.getAllSchoolYears(id);

        return ResponseEntity.ok(cours);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    @GetMapping("/professor/necessary/email/{email}")
    public ResponseEntity<Professor_ToReturnSmall> getNecessaryProfByEmail(@PathVariable @Valid String email) throws UserException {
        Professor_ToReturnSmall prof = professorService.getNecessaryProfByEmail(email);

        return ResponseEntity.ok(prof);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    @GetMapping("/professor/email/{email}")
    public ResponseEntity<Professor_ToReturn> getAllInfoProfByEmail(@PathVariable @Valid String email) throws UserException {
        Professor_ToReturn prof = professorService.getAllInfoProfByEmail(email);

        return ResponseEntity.ok(prof);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR', 'SCOPE_STUDENT')")
    @GetMapping("/professor/{id}")
    public ResponseEntity<Professor_ToReturn> getAllInfoProfBID(@PathVariable @Valid Integer id) throws UserException {
        Professor_ToReturn prof = professorService.getAllInfoProfByID(id);

        return ResponseEntity.ok(prof);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/professors")
    public ResponseEntity<List<Professor_ToReturn>> getAllProfessors(){
        List<Professor_ToReturn> professorToReturnList = professorService.getAllProfessors();

        return ResponseEntity.ok(professorToReturnList);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/professor/department/{department}")
    public ResponseEntity<List<Professor_ToReturn>> getAllProfessorsByDepartment(@PathVariable @Valid Departement department){
        List<Professor_ToReturn> professorToReturnList =
                professorService.getAllProfessorsByDepartement(department);

        return ResponseEntity.ok(professorToReturnList);
    }

    @PreAuthorize("hasAuthority('SCOPE_PROFESSOR')")
    @PostMapping("/professor/note")
    public ResponseEntity<Boolean> addNoteToStudent(@RequestBody @Valid Note_ToSend noteToSend) throws CoursException, UserException {
        boolean  add= professorService.noteToCours(noteToSend);

        return ResponseEntity.accepted().body(add);

    }

    @PreAuthorize("hasAuthority('SCOPE_PROFESSOR')")
    @PostMapping("/professor/notes")
    public ResponseEntity<Boolean> addNotesToStudent(@RequestBody @Valid List<Note_ToSend> noteToSend) throws CoursException, UserException {
        boolean  add= professorService.notesToCours(noteToSend);

        return ResponseEntity.accepted().body(add);

    }




}
