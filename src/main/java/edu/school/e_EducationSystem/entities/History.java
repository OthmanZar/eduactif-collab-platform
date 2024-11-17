package edu.school.e_EducationSystem.entities;

import edu.school.e_EducationSystem.enums.SchoolState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class History {

    @NonNull
    @EmbeddedId
    private HistoryID id;

    @NonNull
    @ManyToOne
    private SchoolYear schoolYear;

    @NonNull
    @Enumerated(EnumType.STRING)
    private SchoolState state=SchoolState.In_Progress;

    @MapsId("student")
    @ManyToOne
    private Student student;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedAt;

}
