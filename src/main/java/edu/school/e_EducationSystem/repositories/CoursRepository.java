package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours,String> {
}
