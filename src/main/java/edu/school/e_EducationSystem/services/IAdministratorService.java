package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Admin_ToSend;
import edu.school.e_EducationSystem.dtos.HistoryToSend;
import edu.school.e_EducationSystem.enums.SchoolState;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.UserException;
import jakarta.mail.MessagingException;

import java.util.Date;

public interface IAdministratorService {

    void validateUser(Integer id) throws MessagingException, UserException;
    void addAdmin(Admin_ToSend adminToSend) throws MyAuthenticationException;
    void changeStudentState(HistoryToSend historyToSend) throws UserException;
}
