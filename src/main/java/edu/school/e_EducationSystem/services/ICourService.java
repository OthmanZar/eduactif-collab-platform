package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Cours_ToReturn;
import edu.school.e_EducationSystem.dtos.Cours_ToSend;
import edu.school.e_EducationSystem.dtos.Professor_ToReturn;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.UserException;

import java.util.List;

public interface ICourService {

    Cours_ToReturn addCours(Cours_ToSend cours) throws UserException;
    boolean deleteCours(String coursName) throws CoursException;
    Cours_ToReturn getCourById(String coursName) throws CoursException;
    Professor_ToReturn getProfessor(String coursName) throws CoursException;
    List<String> getAllSchoolYears(String courName) throws CoursException;

}
