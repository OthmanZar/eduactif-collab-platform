package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.Request_ToReturn;
import edu.school.e_EducationSystem.dtos.Request_ToSend;
import edu.school.e_EducationSystem.entities.*;
import edu.school.e_EducationSystem.enums.State;
import org.springframework.stereotype.Service;

@Service
public class RequestMapper {

    public Request toSend(Request_ToSend requestToSend){
        Request request = new Request();
        request.setState(State.Pending);
        request.setPurpose(requestToSend.purpose());
        request.setTitle(requestToSend.title());

        return request;
    }

    public Request_ToReturn toReturn(Request request){
        String userType = "";
        if (request.getUser() instanceof Student){
            userType="student";
        }else if(request.getUser() instanceof Professor){
            userType="professor";
        }
        
        return new Request_ToReturn(

                request.getProject().getName(),
                request.getTitle(),
                request.getState(),
                request.getUser().getFirstName()+" "+request.getUser().getLastName(),
                request.getUser().getId(),
                request.getPurpose(),
                request.getCreatedAt(),
                userType
                );
    }
}
