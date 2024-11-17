package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.SchoolYear_ToReturn;
import edu.school.e_EducationSystem.entities.SchoolYear;
import org.springframework.stereotype.Service;

@Service
public class SchoolYearMapper {

    public SchoolYear_ToReturn toReturn(SchoolYear schoolYear){

        return new SchoolYear_ToReturn(
                schoolYear.getName(),
                schoolYear.getType(),
                schoolYear.getMaxStudents(),
                schoolYear.getMaxCours(),
                schoolYear.getDepartement());
    }


    public SchoolYear toSend(SchoolYear_ToReturn schoolYearToReturn){
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setName(schoolYearToReturn.name());
        schoolYear.setType(schoolYearToReturn.type());
        schoolYear.setDepartement(schoolYearToReturn.departement());
        schoolYear.setMaxStudents(schoolYearToReturn.maxStudents());
        schoolYear.setMaxCours(schoolYearToReturn.maxCours());


    return schoolYear;
    }

}
