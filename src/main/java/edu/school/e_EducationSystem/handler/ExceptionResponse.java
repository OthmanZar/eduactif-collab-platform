package edu.school.e_EducationSystem.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private Integer businessCode;
    private String businessErrorDescription;
    private String error;
    private Set<String> validationsErrors;
    private Map<String,String> errors;
}
