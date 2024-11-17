package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.*;
import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.SchoolYearException;
import edu.school.e_EducationSystem.exceptions.UserException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface IStudentSevice {

    StudentDTO_ToReturn addStudent (StudentDTO_ToSend student) throws SchoolYearException, MyAuthenticationException, MessagingException, UserException, FilesException, IOException;
    boolean deleteStudent(Integer id) throws UserException;
    StudentDTO_ToReturn getStudentById(Integer id) throws UserException;
    StudentDTO_ToReturn getStudentByEmail(String email) throws UserException;
    List<StudentDTO_ToReturn> getAllStudents();
//    List<StudentDTO_ToReturn> getAllStudentsByShoolYear(String schoolYear);
    List<StudentDTO_ToReturn> getAllStudentsIfTuteur();
    List<StudentDTO_ToReturn> getAllStudentsIfOld();
    List<Note_ToReturn> getAllNotes(String email) throws UserException;
    List<String> getAllCours(Integer id) throws UserException, SchoolYearException;
    List<StudentDTO_ToReturn> getAllStudentsIfNoEnbled();
    List<HistoryToReturn> getSchoolHistory(String email) throws UserException;
    StudentDTO_ToReturnSmall getNecessaryStudentByEmail(String email) throws UserException;
}
