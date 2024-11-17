package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.*;
import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.UserException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IProfessorService {

    Professor_ToReturn addProfessor(Professor_ToSend professor) throws MyAuthenticationException, MessagingException, UserException, FilesException, IOException;
    boolean deleteProfessor(Integer id,Integer newProfId) throws UserException;
    List<SchoolYearsAndCourses_DTO> getAllCourses(Integer id) throws  UserException;
    Set<String> getAllSchoolYears(Integer id) throws  UserException;
    List<Professor_ToReturn> getAllProfessors();
    List<Professor_ToReturn> getAllProfessorsByDepartement(Departement departement);
    boolean noteToCours(Note_ToSend note) throws UserException, CoursException;
    boolean notesToCours(List<Note_ToSend> notes) throws UserException, CoursException;
    List<Professor_ToReturn> getAllProfessorsIfNoEnabled();
    Professor_ToReturnSmall getNecessaryProfByEmail(String email) throws UserException;
    Professor_ToReturn getAllInfoProfByEmail(String email) throws UserException;
    Professor_ToReturn getAllInfoProfByID(Integer id) throws UserException;
}
