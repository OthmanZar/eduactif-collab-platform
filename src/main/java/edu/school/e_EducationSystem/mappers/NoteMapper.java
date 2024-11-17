package edu.school.e_EducationSystem.mappers;


import edu.school.e_EducationSystem.dtos.Note_ToReturn;
import edu.school.e_EducationSystem.entities.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteMapper {

    public Note_ToReturn toReturn(Note note){
        return new Note_ToReturn(
                note.getValue(),
                note.getComment(),
                note.getCours().getCoursName()
        );
    }

}
