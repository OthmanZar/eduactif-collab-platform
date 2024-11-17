package edu.school.e_EducationSystem.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class RequestID implements Serializable {
    @ManyToOne
    private Users user;

    @ManyToOne
    private Project project;
}
