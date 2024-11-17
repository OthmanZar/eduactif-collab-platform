package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.StudentDTO_ToReturn;
import edu.school.e_EducationSystem.dtos.StudentDTO_ToReturnSmall;
import edu.school.e_EducationSystem.dtos.StudentDTO_ToSend;
import edu.school.e_EducationSystem.entities.SchoolYear;
import edu.school.e_EducationSystem.entities.Student;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    private final PasswordEncoder passwordEncoder;

    public StudentMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public  Student toStudent(StudentDTO_ToSend studentDTOToSend) {
        Student student = new Student();
        student.setEmail(studentDTOToSend.email());
        student.setBirthday(studentDTOToSend.birthday());
        student.setFirstName(studentDTOToSend.firstName());
        student.setLastName(studentDTOToSend.lastName());
        student.setPassword(passwordEncoder.encode(studentDTOToSend.password()));
        student.setPhone(studentDTOToSend.phone());
        student.setSexe(studentDTOToSend.sexe());

        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setName(studentDTOToSend.schoolYear());

        student.setSchoolYear(schoolYear);


        return student;
    }


    public StudentDTO_ToReturn toReturn(Student student){

      return new StudentDTO_ToReturn(
              student.getId(),
               student.getLastName(),
               student.getFirstName(),
               student.getEmail(),
               student.getPhone(),
               student.getSexe(),
               student.getSchoolYear().getName(),
              student.getProfileImage().getImageName(),
              student.getCreatedAt(),
              student.getBirthday()
       );

    }
    public StudentDTO_ToReturnSmall toReturnSmall(Student student){

        return new StudentDTO_ToReturnSmall(
                student.getId(),
                student.getFirstName()+" "+ student.getLastName(),
                student.getEmail(),
                student.getProfileImage().getImageName()

        );

    }

}
