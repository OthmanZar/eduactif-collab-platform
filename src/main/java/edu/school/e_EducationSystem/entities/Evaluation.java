package edu.school.e_EducationSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Evaluation {

    @EmbeddedId
    private EvaluationID evaluationID;
    @Min(message = "Evaluation Must Be Between 0 and 5", value = 0)
    @Max(message = "Evaluation Must Be Between 0 and 5", value = 5)
    private double evaluation;
    @NotBlank(message = "Please provide a comment")
    @Column(length = 150)
    @Size(max = 250)
    private String comment;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @MapsId("project")
    @ManyToOne
    private Project project;

    @MapsId("user")
    @ManyToOne
    private Users user;
}
