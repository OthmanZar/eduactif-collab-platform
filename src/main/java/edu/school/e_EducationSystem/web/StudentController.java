package edu.school.e_EducationSystem.web;


import edu.school.e_EducationSystem.dtos.*;
import edu.school.e_EducationSystem.exceptions.SchoolYearException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.services.IStudentSevice;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class StudentController {

    private IStudentSevice studentService;


    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO_ToReturn>> getAllStudents(){
        List<StudentDTO_ToReturn> allStudents = studentService.getAllStudents();

        return ResponseEntity.ok(allStudents);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable @Valid Integer id) throws UserException {
        boolean b = studentService.deleteStudent(id);

        return ResponseEntity.ok(b);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO_ToReturn> getStudentById(@PathVariable @Valid Integer id)
            throws UserException {

        StudentDTO_ToReturn student = studentService.getStudentById(id);

        return ResponseEntity.ok(student);

    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/students/email/{email}")
    public ResponseEntity<StudentDTO_ToReturn> getStudentByEmail(@PathVariable @Valid String email) throws UserException {
            System.out.println(email);
        StudentDTO_ToReturn  student = studentService.getStudentByEmail(email);

        return ResponseEntity.ok(student);

    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/students/necessary/email/{email}")
    public ResponseEntity<StudentDTO_ToReturnSmall> getNecessaryStudentByEmail(@PathVariable @Valid String email)
            throws UserException {
        System.out.println(email);
        StudentDTO_ToReturnSmall student = studentService.getNecessaryStudentByEmail(email);

        return ResponseEntity.ok(student);

    }



    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT')")
    @GetMapping("/students/{id}/courses")
    public ResponseEntity<List<String>> getAllCours(@PathVariable @Valid Integer id) throws SchoolYearException, UserException {
        List<String> courses = studentService.getAllCours(id);

        return ResponseEntity.ok(courses);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT')")
    @GetMapping("/students/{email}/notes")
    public ResponseEntity<List<Note_ToReturn>> getAllNotes(@PathVariable @Valid String email) throws UserException {

        List<Note_ToReturn> notes = studentService.getAllNotes(email);

        return ResponseEntity.ok(notes);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT')")
    @GetMapping("/students/{email}/history")
    public ResponseEntity<List<HistoryToReturn>> getHistory(@PathVariable String email) throws UserException {

        List<HistoryToReturn> schoolHistory = studentService.getSchoolHistory(email);

        return ResponseEntity.ok(schoolHistory);
    }



}
