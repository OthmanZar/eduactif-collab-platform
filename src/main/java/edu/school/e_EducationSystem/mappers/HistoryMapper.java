package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.HistoryToReturn;
import edu.school.e_EducationSystem.entities.History;
import org.springframework.stereotype.Service;

@Service
public class HistoryMapper {

    public HistoryToReturn toReturn(History history){

        return new HistoryToReturn(
                history.getSchoolYear().getName(),
                history.getSchoolYear().getType(),
                history.getState(),
                history.getId().getDate()
        );

    }

}
