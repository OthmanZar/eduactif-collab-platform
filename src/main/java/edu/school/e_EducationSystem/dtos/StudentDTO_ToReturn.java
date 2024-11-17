package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.enums.Sexe;


import java.time.LocalDateTime;
import java.util.Date;


public record StudentDTO_ToReturn(  Integer id,
                                    String lastName,
                                  String firstName,
                                  String email,
                                  String phone,
                                  Sexe sexe,
                                  String schoolYear,
                                    String imageName,
                                    LocalDateTime createdDate,
                                    Date birthday


) {
}
