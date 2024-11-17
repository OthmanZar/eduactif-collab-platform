package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.State;
import lombok.NonNull;

public record Request_ToChange(
        @NonNull
        Integer idOwner,
        @NonNull
        Integer idUser,
        @NonNull
        String projectName,
        @NonNull
        State state

) {
}
