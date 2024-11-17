package edu.school.e_EducationSystem.web;


import edu.school.e_EducationSystem.dtos.Evaluation_ToReturn;
import edu.school.e_EducationSystem.dtos.Evaluation_ToSend;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.services.IEvaluationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EvaluationController {

    private IEvaluationService evaluationService;

    @PreAuthorize("hasAnyAuthority('SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @PostMapping("/evaluation")
    public ResponseEntity<Evaluation_ToReturn> addEvaluation(@RequestBody @Valid Evaluation_ToSend evaluation)
            throws ProjectException, UserException {

        Evaluation_ToReturn evaluationToReturn = evaluationService.addEvaluation(evaluation);

        return ResponseEntity.accepted().body(evaluationToReturn);
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/evaluations/{name}")
    public ResponseEntity<List<Evaluation_ToReturn>> getAllEvaluationsOfProject(@PathVariable String name) throws ProjectException {
        List<Evaluation_ToReturn> evaluationsToReturn = evaluationService.getAllEvaluationsOfProject(name);

        return ResponseEntity.accepted().body(evaluationsToReturn);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/evaluations/{name}/mean")
    public ResponseEntity<Double> getTheMoyenneEvaluationsOfProject(@PathVariable String name) throws ProjectException {
        double   meanEvaluation = evaluationService.getTheMoyenneEvaluationsOfProject(name);


        return ResponseEntity.accepted().body(meanEvaluation);
    }
// test if project exist

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/evaluations/{name}/order/ASC")
    public ResponseEntity<List<Evaluation_ToReturn>> getAllEvaluationsOfProjectASC(@PathVariable String name) throws ProjectException {
        List<Evaluation_ToReturn>  evaluationsToReturn = evaluationService.getAllEvaluationsOfProjectASC(name);

        return ResponseEntity.accepted().body(evaluationsToReturn);
    }
    // test if project exist
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/evaluations/{name}/order/DESC")
    public ResponseEntity<List<Evaluation_ToReturn>> getAllEvaluationsOfProjectADESC(@PathVariable String name) throws ProjectException {
        List<Evaluation_ToReturn> evaluationsToReturn = evaluationService.getAllEvaluationsOfProjectADESC(name);

        return ResponseEntity.accepted().body(evaluationsToReturn);
    }
}
