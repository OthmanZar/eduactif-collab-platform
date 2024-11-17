package edu.school.e_EducationSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Cours {
    @Id
    private String coursName;

    @ManyToMany(mappedBy = "courses",fetch = FetchType.EAGER)
    private List<SchoolYear> schoolYears= new ArrayList<>();
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedAt;
    @ManyToOne
    Professor professor;

//    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
//    private List<Note> notes = new ArrayList<>();
}
