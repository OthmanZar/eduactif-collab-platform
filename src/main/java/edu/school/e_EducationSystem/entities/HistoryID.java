package edu.school.e_EducationSystem.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Embeddable
@Data
public class HistoryID implements Serializable {


    @ManyToOne
    private Student student;

    @NotNull
    private Integer date;
}
