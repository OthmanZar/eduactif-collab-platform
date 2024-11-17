package edu.school.e_EducationSystem.web;

import edu.school.e_EducationSystem.dtos.*;
import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.SchoolYearException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.services.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    //private IAuthenticationService authenticationService;
    private IStudentSevice studentService;
    private IProfessorService professorService;
    private IAdministratorService administratorService;
    private final AuthenticationServiceImpl authenticationService;
    @PostMapping("/login")
    public ResponseEntity<Tokens> jwtToken(@RequestBody  Authentification_DTO authentificationDto) throws MyAuthenticationException {
        if (authentificationDto==null){
            throw new MyAuthenticationException("granType is missing");
        }

        String userName = authentificationDto.userName();

        String password = authentificationDto.password();

        boolean withRefreshToken =  authentificationDto.withRefreshToken();

        String granType = authentificationDto.granType();

        String refreshToken = authentificationDto.refreshToken();
      Map<String ,String> keys=  authenticationService.
                login(userName,password,withRefreshToken,granType,refreshToken);

      if(keys.size()<2){
          return ResponseEntity.ok(Tokens.builder().accessToken(keys.get("accessToken")).build());
      }else {
          return ResponseEntity.ok(Tokens.builder().accessToken(keys.get("accessToken")).refreshToken("refreshToken").build());
      }


    }

    @PostMapping("/register/student")
    public ResponseEntity<StudentDTO_ToReturn> addStudents(@ModelAttribute @Valid StudentDTO_ToSend student)
            throws SchoolYearException, MyAuthenticationException, MessagingException, UserException, FilesException, IOException {
            System.out.println(student);

        StudentDTO_ToReturn studentDTOToReturn= studentService.addStudent(student);

//        Map<String, String> response = new HashMap<>();
//        response.put("email", studentDTOToReturn.email());
//        response.put("message", "Student successfully added!");



        return ResponseEntity.accepted().body(studentDTOToReturn);
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String,String>> validateUser(@Valid @RequestParam() String email, @Valid @RequestParam() String code,@Valid @RequestParam() String type)
            throws MessagingException, UserException, MyAuthenticationException {

        String message=  authenticationService.validateUser(email,code,type);
        Map<String,String> resp = new HashMap<>();
        resp.put("message",message);
        return ResponseEntity.ok(resp);
    }


    @PostMapping("/register/professor")
    public ResponseEntity<Professor_ToReturn> addProfessor(@ModelAttribute @Valid Professor_ToSend professor) throws MyAuthenticationException, MessagingException, UserException, FilesException, IOException {
        Professor_ToReturn professorToReturn=professorService.addProfessor(professor);

        return ResponseEntity.accepted().body(professorToReturn);
    }
}
