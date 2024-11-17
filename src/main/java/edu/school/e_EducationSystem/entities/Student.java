package edu.school.e_EducationSystem.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Student extends Users {

    private boolean isTutor=false;
    private boolean isOld=false;

    @ManyToOne(fetch = FetchType.EAGER)
    private SchoolYear schoolYear;

    @OneToMany(mappedBy = "student" ,fetch = FetchType.EAGER)
    List<Project> projects=new ArrayList<>();

    @OneToMany(mappedBy = "student" ,cascade = CascadeType.REMOVE)
    List<Note> notes;

   // @OneToMany(mappedBy = "student" ,cascade = CascadeType.REMOVE)
   // List<History> histories;

}
