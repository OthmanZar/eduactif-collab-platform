package edu.school.e_EducationSystem.dtos;

import edu.school.e_EducationSystem.entities.Cours;
import org.springframework.stereotype.Service;


public record Cours_ToSend (
        String name,
        Integer id

){



}
