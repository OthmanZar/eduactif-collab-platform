package edu.school.e_EducationSystem.web;

import edu.school.e_EducationSystem.dtos.Cours_ToReturn;
import edu.school.e_EducationSystem.dtos.Cours_ToSend;
import edu.school.e_EducationSystem.dtos.Professor_ToReturn;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.services.AuthenticationServiceImpl;
import edu.school.e_EducationSystem.services.ICourService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CourController {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    private ICourService courService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/cours")
    public ResponseEntity<String> addCour(@RequestBody @Valid Cours_ToSend cours) throws UserException {
        Cours_ToReturn coursToReturn  = courService.addCours(cours);

        return ResponseEntity.accepted().body(coursToReturn.name());
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/cours/{name}")
    public ResponseEntity<Boolean> deleteCours(@PathVariable @Valid String name) throws CoursException {
        courService.deleteCours(name);

        return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/cours/{name}")
    public ResponseEntity<String> getCourById(@PathVariable @Valid String name) throws CoursException {
        Cours_ToReturn coursToReturn= courService.getCourById(name);

        return ResponseEntity.ok(coursToReturn.name());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/cours/{name}/professor")
    public ResponseEntity<String> getProfessor(@PathVariable @Valid String name) throws CoursException {

        Professor_ToReturn professorToReturn=courService.getProfessor(name);;


        return ResponseEntity.ok(professorToReturn.firstName()+" "+professorToReturn.lastName());
    }


    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/cours/{name}/schoolYears")
    public ResponseEntity<String> getAllSchoolYears(@PathVariable @Valid String name) throws CoursException {

        List<String>  schoolYears =courService.getAllSchoolYears(name);

        return ResponseEntity.ok(schoolYears.toString());
    }






}
