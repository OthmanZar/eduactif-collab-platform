package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.Cours;
import edu.school.e_EducationSystem.entities.SchoolYear;
import edu.school.e_EducationSystem.entities.Student;
import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolYearRepository extends JpaRepository<SchoolYear,String> {

    List<SchoolYear> findAllByDepartement(Departement departement);
    List<SchoolYear> findAllByType(Type type);
    

}
