package edu.school.e_EducationSystem.entities;


import edu.school.e_EducationSystem.enums.Domaine;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @Id
    @NonNull
    @NotBlank(message = "Please provide a Project Name")
    @Column(length = 70)
    private String name;

    @Size(max = 1000)
    @NotBlank(message = "Please provide a description for your project")
    private String description;


//    @NotBlank(message = "Please provide the video Path")
//    private String videoPath;

    @OneToOne(cascade = CascadeType.ALL)
    private Video video;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL ,optional = true)
    private Pdf report;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedAt;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Domaine domaine;
    private boolean isVisible=true;
    private boolean isOpen=true;
    @NonNull
    @ManyToOne
    private Student student;
    @NonNull
    @ManyToOne
    private Professor professor;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    List<Evaluation> evaluations=new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    List<Request> requests;


}
