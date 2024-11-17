package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.Professor;
import edu.school.e_EducationSystem.entities.Project;
import edu.school.e_EducationSystem.entities.Student;
import edu.school.e_EducationSystem.entities.Users;
import edu.school.e_EducationSystem.enums.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,String> {

    List<Project> findAllByStudent(Student student);
    List<Project> findAllByProfessor(Professor professor);
    List<Project> findAllByDomaine(Domaine domaine);
    void deleteAllByStudent(Users student);
    void deleteAllByProfessor(Users professor);

}
