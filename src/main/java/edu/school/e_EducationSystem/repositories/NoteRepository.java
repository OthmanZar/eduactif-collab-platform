package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.Cours;
import edu.school.e_EducationSystem.entities.Note;
import edu.school.e_EducationSystem.entities.NoteID;
import edu.school.e_EducationSystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, NoteID> {


   List<Note> findAllByStudent(Student student);

   void deleteAllByCours(Cours cours);


}
