package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Evaluation_ToReturn;
import edu.school.e_EducationSystem.dtos.Evaluation_ToSend;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.UserException;

import java.util.List;

public interface IEvaluationService {

    Evaluation_ToReturn addEvaluation(Evaluation_ToSend evaluation) throws UserException, ProjectException;
    List<Evaluation_ToReturn> getAllEvaluationsOfProject(String name) throws ProjectException;
    double getTheMoyenneEvaluationsOfProject(String name) throws ProjectException;
    List<Evaluation_ToReturn> getAllEvaluationsOfProjectASC(String name) throws ProjectException;
    List<Evaluation_ToReturn> getAllEvaluationsOfProjectADESC(String name) throws ProjectException;
}
