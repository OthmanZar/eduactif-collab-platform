package edu.school.e_EducationSystem.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class NoteID implements Serializable {
    @ManyToOne
    private Cours cours;

    @ManyToOne
    private Student student;
}
