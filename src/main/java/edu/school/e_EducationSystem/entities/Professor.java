package edu.school.e_EducationSystem.entities;

import edu.school.e_EducationSystem.enums.Departement;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Professor extends Users {
    @NonNull
    @Enumerated(EnumType.STRING)
    private Departement departement;

    @OneToMany(mappedBy = "professor")
    private List<Cours> coursList= new ArrayList<>();

    @OneToMany(mappedBy = "professor" ,cascade = CascadeType.REMOVE)
    List<Project> projects;



}
