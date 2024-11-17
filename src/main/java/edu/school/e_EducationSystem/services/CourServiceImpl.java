package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.dtos.Cours_ToReturn;
import edu.school.e_EducationSystem.dtos.Cours_ToSend;
import edu.school.e_EducationSystem.dtos.Professor_ToReturn;
import edu.school.e_EducationSystem.entities.Cours;
import edu.school.e_EducationSystem.entities.SchoolYear;
import edu.school.e_EducationSystem.exceptions.CoursException;
import edu.school.e_EducationSystem.exceptions.SchoolYearException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.mappers.CoursMapper;
import edu.school.e_EducationSystem.mappers.ProfessorMapper;
import edu.school.e_EducationSystem.repositories.CoursRepository;
import edu.school.e_EducationSystem.repositories.NoteRepository;
import edu.school.e_EducationSystem.repositories.SchoolYearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor

public class CourServiceImpl implements ICourService {

    private CoursRepository coursRepository;
    private CoursMapper coursMapper;
    private ProfessorMapper professorMapper;
    private SchoolYearRepository schoolYearRepository;
    private NoteRepository noteRepository;
    @Override
    public Cours_ToReturn addCours(Cours_ToSend cours) throws  UserException {
        Cours_ToReturn aReturn = null;
        try {
            aReturn = coursMapper.
                    toReturn(coursRepository.
                            save(coursMapper.toCours(cours)));
        } catch (Exception e) {
            throw new UserException("No Professor found for this id");
        }
        return aReturn;
    }
    @Transactional
    @Override
    public boolean deleteCours(String coursName) throws CoursException {
        Cours cours =coursRepository.findById(coursName).orElseThrow(()->new CoursException("Cours Not Found"));
        List<SchoolYear> schoolYearsList=cours.getSchoolYears();

        for (SchoolYear schoolYear  : schoolYearsList){
            schoolYear.getCourses().remove(cours);
            schoolYearRepository.save(schoolYear);
        }
        cours.getSchoolYears().clear();
        noteRepository.deleteAllByCours(cours);
//        cours.getNotes().clear();
        coursRepository.save(cours);

        try {
            coursRepository.deleteById(coursName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return false;
    }

    @Override
    public Cours_ToReturn getCourById(String coursName) throws CoursException {
        Cours cours = coursRepository.findById(coursName).orElseThrow(
                ()->new CoursException("This Cours is Not Found"));

        return coursMapper.toReturn(cours);
    }

    @Override
    public Professor_ToReturn getProfessor(String coursName) throws CoursException {
        Cours cours = coursRepository.findById(coursName).orElseThrow(
                ()->new CoursException("This Cours is Not Found"));


        return professorMapper.toReturn(cours.getProfessor());
    }

    @Override
    public List<String> getAllSchoolYears(String courName) throws CoursException {
        Cours cours = coursRepository.findById(courName).orElseThrow(
                ()->new CoursException("This Cours is Not Found"));

        return cours.getSchoolYears().stream().map(SchoolYear::getName).toList();
    }
}
