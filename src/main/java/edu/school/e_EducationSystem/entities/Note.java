package edu.school.e_EducationSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Note {
    @EmbeddedId
    private NoteID id;

//    @NotBlank(message = "Please provide the note")
    @Min(message = "Note Must Be Between 0 and 20", value = 0)
    @Max(message = "Note Must Be Between 0 and 20", value = 20)
    private double value;
    private String comment;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedAt;

    @MapsId("cours")
    @ManyToOne
    private Cours cours;

    @MapsId("student")
    @ManyToOne
    private Student student;
}
