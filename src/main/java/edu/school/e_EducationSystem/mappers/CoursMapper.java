package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.Cours_ToReturn;
import edu.school.e_EducationSystem.dtos.Cours_ToSend;
import edu.school.e_EducationSystem.entities.Cours;
import edu.school.e_EducationSystem.entities.Professor;
import edu.school.e_EducationSystem.entities.SchoolYear;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CoursMapper {

    public Cours toCours(Cours_ToSend coursToSend){

        Cours cours = new Cours();
        cours.setCoursName(coursToSend.name());
        Professor professor = new Professor();
        professor.setId(coursToSend.id());
        cours.setProfessor(professor);

        return  cours;

    }

    public Cours_ToReturn toReturn(Cours cours){

        return new Cours_ToReturn(
                cours.getCoursName()

        );
    }
}
