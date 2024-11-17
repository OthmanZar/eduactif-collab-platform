package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.entities.Image;
import edu.school.e_EducationSystem.entities.Pdf;
import edu.school.e_EducationSystem.entities.Video;
import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.repositories.ImageRepository;
import edu.school.e_EducationSystem.repositories.PdfRepository;
import edu.school.e_EducationSystem.repositories.VideoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilesService {

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

    private Set<String> IMAGE_MIME_TYPES;
    private Set<String> VIDEO_MIME_TYPES;
    private Set<String> PDF_MIME_TYPES;

    @PostConstruct
    public void init() {
        IMAGE_MIME_TYPES = new HashSet<>(Arrays.asList(imageMimeTypes.split(",")));
        VIDEO_MIME_TYPES = new HashSet<>(Arrays.asList(videoMimeTypes.split(",")));
        PDF_MIME_TYPES = new HashSet<>(Arrays.asList(pdfMimeTypes.split(",")));
    }
    private final PdfRepository pdfRepository;
    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;


    public Video uploadVideo(MultipartFile video) throws IOException, FilesException {
        if (video != null && VIDEO_MIME_TYPES.contains(video.getContentType())) {
            String filePath = saveFile(video, "video");
            Video videoEntity = new Video();
            videoEntity.setVideoName(video.getOriginalFilename());
            videoEntity.setVideoPath(filePath);
            videoEntity.setSize(video.getSize());
            videoEntity.setVideoType("video");
            // project.setVideo(videoEntity);
            videoRepository.save(videoEntity);
            return videoEntity;
        }

        throw new FilesException("Invalid Video file Or file is Missing ");
    }


    public Pdf uploadReport(MultipartFile report) throws FilesException, IOException {
        Pdf pdfEntity = new Pdf();
        if (report != null && PDF_MIME_TYPES.contains(report.getContentType())) {
            String filePath = saveFile(report, "pdf");

            pdfEntity.setPdfName(report.getOriginalFilename());
            pdfEntity.setPdfPath(filePath);
            pdfEntity.setSize(report.getSize());
            pdfEntity.setPdfType("pdf");
            pdfRepository.save(pdfEntity);
            return pdfEntity;

        }

        throw new FilesException("Invalid PDF file Or file is Missing");


    }


    public Set<Image> uploadImages(List<MultipartFile> images) throws FilesException, IOException {
        Set<Image> imageEntities = new HashSet<>();
        for (MultipartFile image : images) {
            if (!IMAGE_MIME_TYPES.contains(image.getContentType())) {
                throw new FilesException("Invalid image file: " + image.getOriginalFilename());
            }
            String filePath = saveFile(image, "image");
            Image imageEntity = new Image();
            imageEntity.setImageName(image.getOriginalFilename());
            imageEntity.setImagePath(filePath);
            imageEntity.setSize(image.getSize());
            imageEntity.setImageType("image");
            imageEntities.add(imageEntity);
            imageRepository.save(imageEntity);
        }
        return imageEntities;
    }

    public Image uploadImage(MultipartFile image) throws IOException, FilesException {
        Image imageEntity = new Image();
        if (image != null && IMAGE_MIME_TYPES.contains(image.getContentType())) {
            String filePath = saveFile(image, "image");

            imageEntity.setImageType(image.getOriginalFilename());
            imageEntity.setImagePath(filePath);
            imageEntity.setSize(image.getSize());
            imageEntity.setImageType("image");
            imageEntity.setImageName(image.getOriginalFilename());
            imageRepository.save(imageEntity);
            return imageEntity;

        }

        throw new FilesException("Invalid Image file Or file is Missing");


    }

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
