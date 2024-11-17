package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.Professor;
import edu.school.e_EducationSystem.entities.Users;
import edu.school.e_EducationSystem.enums.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor,Integer> {
    List<Professor> findAllByDepartement(Departement departement);


    Professor findByEmail(String email);
}
