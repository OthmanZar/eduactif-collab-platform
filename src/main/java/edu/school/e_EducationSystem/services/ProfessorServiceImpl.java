package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.*;
import edu.school.e_EducationSystem.entities.*;
import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.mappers.CoursMapper;
import edu.school.e_EducationSystem.mappers.ProfessorMapper;
import edu.school.e_EducationSystem.repositories.*;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProfessorServiceImpl implements IProfessorService {

   // private final UsersRepository usersRepository;
    //private UsersRepository usersRepository;
    private ProfessorMapper professorMapper;
    private CoursMapper coursMapper;
    private NoteRepository noteRepository;
    private CoursRepository coursRepository;
    private ProjectRepository projectRepository;
    private ProfessorRepository professorRepository;
    private StudentRepository studentRepository;
    private RoleRepository roleRepository;
    private IAdministratorService administratorService;
    private FilesService filesService;
    @Override
    public Professor_ToReturn addProfessor(Professor_ToSend professor) throws MyAuthenticationException, MessagingException, UserException, FilesException, IOException {
        var role = roleRepository.findById("PROFESSOR").orElseThrow(
                () -> new MyAuthenticationException("Role Not Found"));

        Professor professor1 = professorMapper.toProfessor(professor);
        professor1.setRoles(List.of(role));
        Image image = filesService.uploadImage(professor.image());
        professor1.setProfileImage(image);
        professor1 = professorRepository.save(professor1);
        administratorService.validateUser(professor1.getId());
        return professorMapper.toReturn(professor1);
    }

    @Override
    public boolean deleteProfessor(Integer id , Integer newProfId) throws UserException {

        Professor professor = professorRepository.findById(id).orElseThrow(
                () -> new UserException("Professor Not Found"));


        Professor newProfessor = professorRepository.findById(newProfId).orElseThrow(
                () -> new UserException("Professor Not Found"));


            professor.getCoursList()
                    .forEach(cours -> {
                        cours.setProfessor(newProfessor);
                        coursRepository.save(cours);});


        projectRepository.deleteAllByProfessor(professor);

        professorRepository.deleteById(id);


        return true;
    }

    @Override
    public List<SchoolYearsAndCourses_DTO> getAllCourses(Integer id) throws  UserException {
       Professor professor = professorRepository.findById(id).orElseThrow(() ->
               new UserException("The Professor is Not Found"));

       List<Cours> courses = new ArrayList<>();

       List<SchoolYearsAndCourses_DTO> schoolYearsAndCoursesDtos = new ArrayList<>();

       if (professor.getCoursList().isEmpty()){
           return schoolYearsAndCoursesDtos;
       }

       courses=professor.getCoursList();

       for(Cours cour : courses){
           schoolYearsAndCoursesDtos.add(
                   new SchoolYearsAndCourses_DTO(
                           cour.getSchoolYears()
                                   .stream()
                                   .map(SchoolYear::getName).collect(Collectors.toList()),
                           cour.getCoursName()
                   )

           );
       }

           ///courses =  professor.
                      //     getCoursList().stream().
                       //    map(cours1 ->coursMapper.toReturn(cours1)).toList();

        return schoolYearsAndCoursesDtos;
    }

    @Override
    public Set<String> getAllSchoolYears(Integer id) throws  UserException {
        Professor professor = professorRepository.findById(id).orElseThrow(() ->
                new UserException("The Professor is Not Found"));

        Set<String> schoolYears = new HashSet<>();

        if(professor.getCoursList().isEmpty()){
            return schoolYears;
        }
            schoolYears = professor.
                    getCoursList().stream().flatMap(cours -> cours.getSchoolYears().
                            stream().map(SchoolYear::getName)).collect(Collectors.toSet());


        return schoolYears;
    }

    @Override
    public List<Professor_ToReturn> getAllProfessors() {
        List<Professor> usersList = professorRepository.findAll();
        List<Professor_ToReturn> professors = new ArrayList<>();
        if(usersList.isEmpty()){
            return professors;
        }
        professors=usersList.stream().map(professor -> professorMapper.toReturn(professor)).toList();
        return professors;
    }

    @Override
    public List<Professor_ToReturn> getAllProfessorsByDepartement(Departement departement) {
       List<Professor> professors= professorRepository.findAllByDepartement(departement);
        List<Professor_ToReturn> professorToReturns = new ArrayList<>();
       if (professors.isEmpty()){
           return professorToReturns;
       }
        professorToReturns=professors.stream().map(professor -> professorMapper.toReturn(professor)).toList();
//        professors.removeIf(professor -> !professor.departement().equals(departement));

        return professorToReturns;
    }

    @Override
    public boolean noteToCours(Note_ToSend note) throws UserException, CoursException {
        Professor professor = professorRepository.findById(note.idProf()).orElseThrow(() ->
                new UserException("The Professor is Not Found"));

        Student student = studentRepository.findById(note.idStudent()).orElseThrow(
                () -> new UserException("Student Not Found"));


        Cours cours = coursRepository.findById(note.coursName()).orElseThrow(
                () -> new CoursException("Cours Not Found"));
//        cours.setCoursName(note.coursName());
       List<String> list =  (professor).getCoursList().stream().map(Cours::getCoursName).toList();

       if (!list.contains(note.coursName())){
           throw new CoursException("This prof is not The prof Of this cours");
       }

        NoteID noteID = new NoteID();
        noteID.setCours(cours);
        noteID.setStudent(student);

        Note note1 = new Note();
        note1.setId(noteID);
        note1.setValue(note.value());
        note1.setComment(note.comment());
//        note1.setDate(note.date());

        try {
            noteRepository.save(note1);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public boolean notesToCours(List<Note_ToSend> notes) throws UserException, CoursException {

        for (Note_ToSend note:notes){
            noteToCours(note);
        }


        return true;
    }

    @Override
    public List<Professor_ToReturn> getAllProfessorsIfNoEnabled() {
        List<Professor> professors=  professorRepository.findAll();
        List<Professor_ToReturn> professorToReturns = new ArrayList<>();
        if (professors.isEmpty()){
            return professorToReturns;
        }
        for (Professor professor : professors){
            if(!professor.isEnabled()){
                professorToReturns.add(professorMapper.toReturn(professor));
            }
        }
       // professorToReturns=professors.stream().map(professor -> professorMapper.toReturn(professor)).toList();
        return professorToReturns;
    }

    @Override
    public Professor_ToReturnSmall getNecessaryProfByEmail(String email) throws UserException {
        Professor professor = professorRepository.findByEmail(email);
        if(professor==null){
            throw new UserException("Professor Not Found");
        }

        return professorMapper.toReturnSmall(professor);
    }

    @Override
    public Professor_ToReturn getAllInfoProfByEmail(String email) throws UserException {
        Professor professor = professorRepository.findByEmail(email);
        if(professor==null){
            throw new UserException("Professor Not Found");
        }

        return professorMapper.toReturn(professor);
    }

    @Override
    public Professor_ToReturn getAllInfoProfByID(Integer id) throws UserException {
        Professor professor = professorRepository.findById(id).
                orElseThrow(() -> new UserException("Professor Not Found"));


        return professorMapper.toReturn(professor);
    }
}
