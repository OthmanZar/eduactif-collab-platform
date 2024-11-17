package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.Evaluation;
import edu.school.e_EducationSystem.entities.EvaluationID;
import edu.school.e_EducationSystem.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, EvaluationID> {

    List<Evaluation> findAllByProject(Project project);
    List<Evaluation> findAllByProjectOrderByEvaluationAsc(Project project);
    List<Evaluation> findAllByProjectOrderByEvaluationDesc(Project project);
}
