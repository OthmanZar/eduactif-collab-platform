package edu.school.e_EducationSystem.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class EvaluationID implements Serializable {

    @ManyToOne
    private Project project;

    @ManyToOne
    private Users user;
}
