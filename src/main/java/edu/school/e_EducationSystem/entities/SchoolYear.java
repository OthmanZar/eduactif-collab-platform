package edu.school.e_EducationSystem.entities;

import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class SchoolYear {

    @Id
    @NotBlank(message = "Please provide the name of this SchoolYear")
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Departement departement;
    @Min(message = "The Minimum number Of Students in a SchoolYear is 15 ", value = 15)
    @Max(message = "The Maximum number Of Students in a SchoolYear is 35", value = 35)
    private int maxStudents;
    @Min(message = "The Minimum Courses should be 12", value = 12)
    @Max(message = "The Maximum Courses should be 16", value = 16)
    private int maxCours;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedAt;


    @OneToMany(mappedBy = "schoolYear",fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
    private List<Student> students = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Cours> courses= new ArrayList<>();;

    //@OneToMany(mappedBy = "schoolYear",fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
    //private List<History> histories = new ArrayList<>();
}
