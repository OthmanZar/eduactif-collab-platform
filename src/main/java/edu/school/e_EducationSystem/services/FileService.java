package edu.school.e_EducationSystem.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${upload.images.directory}")
    private String imagesUploadDirectory;

    @Value("${upload.videos.directory}")
    private String videosUploadDirectory;

    @Value("${upload.pdfs.directory}")
    private String pdfsUploadDirectory;
    @Value("${image.mime.types}")
    private String imageMimeTypes;

    @Value("${video.mime.types}")
    private String videoMimeTypes;

    @Value("${pdf.mime.types}")
    private String pdfMimeTypes;


    public String saveFile(MultipartFile file, String fileType) throws IOException {
        String directoryPath;

        switch (fileType) {
            case "image":
                directoryPath = imagesUploadDirectory;
                break;
            case "video":
                directoryPath = videosUploadDirectory;
                break;
            case "pdf":
                directoryPath = pdfsUploadDirectory;
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            System.out.println("*****");
        }
        Path videoPath = Paths.get(directoryPath, file.getOriginalFilename());
        String filePath = directoryPath + "/" + file.getOriginalFilename();
        file.transferTo(videoPath); // Save file to the specified path
        return filePath; // Return the path where the file is saved
    }
}
