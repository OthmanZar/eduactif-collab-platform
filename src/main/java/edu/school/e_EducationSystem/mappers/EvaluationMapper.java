package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.Evaluation_ToReturn;
import edu.school.e_EducationSystem.dtos.Evaluation_ToSend;
import edu.school.e_EducationSystem.entities.Evaluation;

import org.springframework.stereotype.Service;

@Service
public class EvaluationMapper {

    public Evaluation toSend(Evaluation_ToSend evaluationToSend){

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluation(evaluationToSend.evaluation());
//        evaluation.setDate(evaluationToSend.date());
        evaluation.setComment(evaluationToSend.comment());

        return evaluation;

    }


    public Evaluation_ToReturn toReturn(Evaluation evaluation){
        return new Evaluation_ToReturn(evaluation.getEvaluation(),
                evaluation.getComment(),
                evaluation.getEvaluationID().getUser().getFirstName()+
                        " "+evaluation.getEvaluationID().getUser().getLastName(),
                evaluation.getCreatedAt(),
                evaluation.getEvaluationID().getUser().getId(),
                evaluation.getEvaluationID().getUser().getProfileImage().getImageName());
    }

}
