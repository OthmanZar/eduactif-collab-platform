package edu.school.e_EducationSystem.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record Project_ToSendFinal(
        Project_ToSend projectToSend,
        MultipartFile video,
        List<MultipartFile> images,
        MultipartFile report

) {
}
