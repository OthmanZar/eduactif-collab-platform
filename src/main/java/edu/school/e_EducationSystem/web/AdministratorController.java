package edu.school.e_EducationSystem.web;

import edu.school.e_EducationSystem.dtos.Admin_ToSend;
import edu.school.e_EducationSystem.dtos.HistoryToSend;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.UserException;

import edu.school.e_EducationSystem.services.IAdministratorService;
import jakarta.mail.MessagingException;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
public class AdministratorController {


    private IAdministratorService administratorService;

    @GetMapping("/activation-code")
    public ResponseEntity<Boolean> sendValidationCode(@RequestParam Integer id)
            throws MessagingException, UserException {

        administratorService.validateUser(id);

        return ResponseEntity.ok(true);
    }



    @PostMapping("/admin")
    public ResponseEntity<String> addAdmin(@RequestBody Admin_ToSend adminToSend) throws MyAuthenticationException {

        administratorService.addAdmin(adminToSend);

        return ResponseEntity.ok("Admin Created");
    }

    @PatchMapping("/student/state")
    public ResponseEntity<String> changeStudentState(@Valid @RequestBody HistoryToSend historyToSend) throws UserException {

        administratorService.changeStudentState(historyToSend);

        return ResponseEntity.ok("State Changed");
    }
}
