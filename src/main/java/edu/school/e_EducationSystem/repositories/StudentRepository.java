package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.dtos.StudentDTO_ToReturn;
import edu.school.e_EducationSystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    Student findByEmail(String email);

}
