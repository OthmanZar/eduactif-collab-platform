package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.History;
import edu.school.e_EducationSystem.entities.HistoryID;
import edu.school.e_EducationSystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, HistoryID> {

    List<History> findAllByStudent(Student student);

    void deleteAllByStudent(Student student);
}
