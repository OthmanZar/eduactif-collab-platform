package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.*;
import edu.school.e_EducationSystem.entities.*;

import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.SchoolYearException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.mappers.HistoryMapper;
import edu.school.e_EducationSystem.mappers.NoteMapper;
import edu.school.e_EducationSystem.mappers.StudentMapper;
import edu.school.e_EducationSystem.repositories.*;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class StudentServiceImpl implements IStudentSevice {

    private final FilesService filesService;
    // private UsersRepository usersRepository;
    private StudentMapper studentMapper;
    private ISchoolYearService schoolYearService;
    private NoteRepository noteRepository;
    private NoteMapper noteMapper;
    private ProjectRepository projectRepository;
    private StudentRepository studentRepository;
    private RoleRepository roleRepository;
    private IAdministratorService administratorService;
    private HistoryRepository historyRepository;
    private HistoryMapper historyMapper;
    private TokenRepository tokenRepository;
    @Override
    public StudentDTO_ToReturn addStudent(StudentDTO_ToSend student) throws SchoolYearException, MyAuthenticationException, MessagingException, UserException, FilesException, IOException {
        Student student1;

        SchoolYear_ToReturn schoolYear1 = schoolYearService.getSchoolYearById(student.schoolYear());

        if(schoolYearService.getAllStudents(student.schoolYear()).size()>=schoolYear1.maxStudents())  {
            throw new SchoolYearException("Theres No place foe more students");
        }

        var role = roleRepository.findById("STUDENT").orElseThrow(
                () -> new MyAuthenticationException("Role Not Found"));
        student1 = studentMapper.toStudent(student);
        student1.setRoles(List.of(role));

        student1  = studentRepository.save(student1);
        History history = new History();
       HistoryID historyID = new HistoryID();
       historyID.setStudent(student1);
        historyID.setDate(LocalDateTime.now().getYear());
        history.setId(historyID);
       SchoolYear schoolYear = new SchoolYear();
       schoolYear.setName(student.schoolYear());
        history.setSchoolYear(schoolYear);

        Image image = filesService.uploadImage(student.image());
        student1.setProfileImage(image);

        historyRepository.save(history);
        administratorService.validateUser(student1.getId());

        return studentMapper.toReturn(student1);
    }


    @Override
    public boolean deleteStudent(Integer id) throws UserException {


        Student student =  studentRepository.findById(id).orElseThrow(()->
                new UserException("Student not found"));

        projectRepository.deleteAllByStudent(student);
        historyRepository.deleteAllByStudent(student);
        tokenRepository.deleteAllByUser(student);
            studentRepository.deleteById(id);

        return true;
    }

    @Override
    public StudentDTO_ToReturn getStudentById(Integer id) throws UserException {

        Student student = studentRepository.findById(id).orElseThrow(()->
                new UserException("Student not found"));

        return studentMapper.toReturn(student);
    }

    @Override
    public StudentDTO_ToReturn getStudentByEmail(String email) throws UserException {
        StudentDTO_ToReturn  studentDTOToReturn;

        try {
            studentDTOToReturn =studentMapper.
                    toReturn( studentRepository.findByEmail(email)) ;
        }catch (Exception e){
            throw new UserException("Student Not Found");
        }

        return studentDTOToReturn;
    }

    @Override
    public List<StudentDTO_ToReturn> getAllStudents() {

        List<StudentDTO_ToReturn> students = new ArrayList<>();

        for (Student user: getStudents()){

                students.add(studentMapper.toReturn(user));

        }

        return students;
    }

    private List<Student> getStudents(){
        List<Student> list = new ArrayList<>() ;

            list = studentRepository.findAll();

        return list;
    }

    @Override
    public List<StudentDTO_ToReturn> getAllStudentsIfTuteur() {

        List<StudentDTO_ToReturn> students = new ArrayList<>();

        for (Student user: getStudents()){


                if(user.isTutor()){
                    students.add(studentMapper.toReturn(user));
                }
        }

        return students;
    }

    @Override
    public List<StudentDTO_ToReturn> getAllStudentsIfOld() {
        List<StudentDTO_ToReturn> students = new ArrayList<>();

        for (Student user: getStudents()){


                if(( user).isOld()){
                    students.add(studentMapper.toReturn( user));
                }

        }

        return students;
    }

    @Override
    public List<Note_ToReturn> getAllNotes(String email) throws UserException {
        Student student = studentRepository.findByEmail(email);

        if(student==null){
            throw new UserException("Student Not Found");
        }


        List<Note> notes = noteRepository.findAllByStudent(student);

        return notes.stream().map(note -> noteMapper.toReturn(note)).toList();
    }

    @Override
    public List<String> getAllCours(Integer id) throws UserException, SchoolYearException {

        StudentDTO_ToReturn student = getStudentById(id);

        return schoolYearService.getAllCoursesOfSchoolYear(student.schoolYear());
    }

    @Override
    public List<StudentDTO_ToReturn> getAllStudentsIfNoEnbled() {
        List<Student> students =getStudents();
        List<StudentDTO_ToReturn> studentDTOToReturns =new ArrayList<>();
        if(students.isEmpty()){
            return studentDTOToReturns;
        }
        for (Student student:students){
            if(!student.isEnabled()){
                studentDTOToReturns.add(studentMapper.toReturn(student));
            }
        }
        return studentDTOToReturns;
    }

    @Override
    public List<HistoryToReturn> getSchoolHistory(String email) throws UserException {

        Student student = studentRepository.findByEmail(email);

        if(student==null){
            throw new UserException("User Not Found");
        }

        List<History> histories = historyRepository.findAllByStudent(student);
        List<HistoryToReturn> historyToReturns=new ArrayList<>();

        if(histories.isEmpty()){
            return historyToReturns;
        }else {
            return histories.stream()
                    .map(history -> historyMapper.toReturn(history)).toList();
        }



    }

    @Override
    public StudentDTO_ToReturnSmall getNecessaryStudentByEmail(String email) throws UserException {
        StudentDTO_ToReturnSmall  studentDTOToReturn;

        try {
            studentDTOToReturn =studentMapper.
                    toReturnSmall( studentRepository.findByEmail(email)) ;
        }catch (Exception e){
            throw new UserException("Student Not Found");
        }

        return studentDTOToReturn;
    }



}
