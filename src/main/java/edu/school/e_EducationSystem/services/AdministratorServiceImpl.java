package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Admin_ToSend;
import edu.school.e_EducationSystem.dtos.HistoryToSend;
import edu.school.e_EducationSystem.entities.*;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.mappers.AdminMapper;
import edu.school.e_EducationSystem.repositories.HistoryRepository;
import edu.school.e_EducationSystem.repositories.RoleRepository;
import edu.school.e_EducationSystem.repositories.StudentRepository;
import edu.school.e_EducationSystem.repositories.UsersRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AdministratorServiceImpl implements IAdministratorService {
    private final AdminMapper adminMapper;
    private final RoleRepository roleRepository;
    private AuthenticationServiceImpl authenticationService;
    private UsersRepository usersRepository;
    private HistoryRepository historyRepository;
    private StudentRepository studentRepository;

    @Override
    public void validateUser(Integer id) throws MessagingException, UserException {
        Users users = usersRepository.findById(id).orElseThrow(()
                -> new UserException("User Not Found"));

        if(!users.isEnabled()){
            authenticationService.sendValidationEmail(users);
        }else {
            throw new UserException("User Already Enabled");
        }


    }

    @Override
    public void addAdmin(Admin_ToSend adminToSend) throws MyAuthenticationException {

        Administrator administrator = adminMapper.toAdmin(adminToSend);
        var role = roleRepository.findById("ADMIN").orElseThrow(
                () -> new MyAuthenticationException("Role Not Found"));

        administrator.setRoles(List.of(role));
        usersRepository.save(administrator);
    }


    @Transactional
    @Override
    public void changeStudentState(HistoryToSend historyToSend) throws UserException {
        Student student = studentRepository.findByEmail(historyToSend.studentEmail());

        if(student==null){
          throw new UserException("Student Not Found");
        }


        HistoryID historyID = new HistoryID();
        historyID.setStudent(student);
        historyID.setDate(historyToSend.date());

        History history = historyRepository.findById(historyID)
               .orElseThrow(() ->new UserException(
                        "This Student with this Date Not registered in the History of SchoolYears"));

        history.setState(historyToSend.state());


    }
}
