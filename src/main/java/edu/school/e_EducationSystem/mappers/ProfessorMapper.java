package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.Professor_ToReturn;
import edu.school.e_EducationSystem.dtos.Professor_ToReturnSmall;
import edu.school.e_EducationSystem.dtos.Professor_ToSend;
import edu.school.e_EducationSystem.entities.Professor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessorMapper {
    private PasswordEncoder passwordEncoder;

    public ProfessorMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public   Professor toProfessor(Professor_ToSend professorToSend){
        Professor professor = new Professor();
        professor.setDepartement(professorToSend.departement());
        professor.setBirthday(professorToSend.birthday());
        professor.setEmail(professorToSend.email());
        professor.setFirstName(professorToSend.firstName());
        professor.setLastName(professorToSend.lastName());
        professor.setPassword(passwordEncoder.encode(professorToSend.password()));
        professor.setSexe(professorToSend.sexe());
        professor.setPhone(professorToSend.phone());

        return professor;


    }


  public   Professor_ToReturn toReturn(Professor professor){

        return new Professor_ToReturn(
                professor.getId(),
                professor.getLastName(),
                professor.getFirstName(),
                professor.getEmail(),
                professor.getPhone(),
                professor.getSexe(),
                professor.getDepartement(),
                professor.getProfileImage().getImageName(),
                professor.getCreatedAt(),
                professor.getBirthday()


        );
    }

    public Professor_ToReturnSmall toReturnSmall(Professor professor){

        return new Professor_ToReturnSmall(
                professor.getId(),
                professor.getFirstName()+" " +professor.getLastName(),
                professor.getEmail(),
                professor.getProfileImage().getImageName()

        );
    }

}
