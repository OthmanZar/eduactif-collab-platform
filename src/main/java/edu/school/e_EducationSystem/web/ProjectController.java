package edu.school.e_EducationSystem.web;

import edu.school.e_EducationSystem.dtos.Project_ToReturn;
import edu.school.e_EducationSystem.dtos.Project_ToReturnTotal;
import edu.school.e_EducationSystem.dtos.Project_ToSend;
import edu.school.e_EducationSystem.dtos.Project_ToSendFinal;
import edu.school.e_EducationSystem.enums.Domaine;
import edu.school.e_EducationSystem.enums.Type;
import edu.school.e_EducationSystem.exceptions.FilesException;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.services.IProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final IProjectService projectService;

    @PreAuthorize("hasAnyAuthority('SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @PostMapping("/project")
    public ResponseEntity<Boolean> addProject(@ModelAttribute @Valid Project_ToSend project) throws UserException, ProjectException, IOException, FilesException {
      // System.out.println(project.projectToSend());
        String projectName=projectService.addProject(project);

        return ResponseEntity.accepted().body(true);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/project/{name}")
    public ResponseEntity<Project_ToReturn> getProjectByName(@PathVariable @Valid String name) throws ProjectException {
        Project_ToReturn  project=projectService.getProjectByName(name);

        return ResponseEntity.ok().body(project);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/project/info/{name}")
    public ResponseEntity<Project_ToReturnTotal> getAllInformations(@PathVariable @Valid String name) throws ProjectException {
        Project_ToReturnTotal project=projectService.getAllInformations(name);

        return ResponseEntity.ok().body(project);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/projects/student/{id}")
    public ResponseEntity<List<Project_ToReturnTotal>> getAllProjectsByStudent(@PathVariable @Valid Integer id) throws UserException {
       List<Project_ToReturnTotal>   project=projectService.getAllProjectsByStudent(id);

        return ResponseEntity.ok().body(project);
    }

    @Value("${upload.images.directory}")
    private String imagesUploadDirectory;
    @Value("${upload.videos.directory}")
    private String videosUploadDirectory;
    @Value("${upload.pdfs.directory}")
    private String pdfsUploadDirectory;

    //@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        Path imagePath = Paths.get(imagesUploadDirectory).resolve(imageName);
        Resource resource = new FileSystemResource(imagePath);
        System.out.println(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
    @GetMapping("/videos/{videoName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String videoName) {
        Path videoPath = Paths.get(videosUploadDirectory).resolve(videoName);
        Resource resource = new FileSystemResource(videoPath);
        System.out.println(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4")) // Adjust based on the image type
                .body(resource);
    }

    @GetMapping("/report/{report}")
    public ResponseEntity<Resource> getReport(@PathVariable String report) {
        Path filePath = Paths.get(pdfsUploadDirectory).resolve(report);
        Resource resource = new FileSystemResource(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report + "\"")
                .body(resource);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/projects/professor/{id}")
    public ResponseEntity<List<Project_ToReturnTotal>> getAllProjectsByProfessor(@PathVariable @Valid Integer id){
        List<Project_ToReturnTotal>   project=projectService.getAllProjectsByProfessor(id);

        return ResponseEntity.ok().body(project);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/projects/domaine/{domaine}")
    public ResponseEntity<Object> getAllProjectsByDomaine(@PathVariable @Valid Domaine domaine){
        List<Project_ToReturn>  project=projectService.getAllProjectsByDomaine(domaine);


        return ResponseEntity.ok().body(project);
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/projects")
    public ResponseEntity<List<Project_ToReturn>> getAllProjects(){
        List<Project_ToReturn>  project=projectService.getAllProjects();


        return ResponseEntity.ok().body(project);
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_PROFESSOR')")
    @DeleteMapping("/project/{name}")
    public ResponseEntity<Object> deleteProject(@PathVariable @Valid String name) throws ProjectException {
        boolean  deleted =  projectService.deleteProject(name);

        return ResponseEntity.ok().body(deleted);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_PROFESSOR')")
    @PatchMapping("/project/{name}/visibility")
    public ResponseEntity<Object> changeVisibilityOfProject(@PathVariable String name ,@RequestParam @Valid boolean isVisible) throws ProjectException {
        boolean changed=projectService.changeVisibilityOfProject(name,isVisible);

        return ResponseEntity.accepted().body(changed);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_PROFESSOR')")
    @PatchMapping("/project/{name}/opening")
    public ResponseEntity<Object> changeIsOpenOfProject(@PathVariable String name ,@RequestParam @Valid boolean isOpen) throws ProjectException {
        boolean  changed=projectService.changeIsOpenOfProject(name,isOpen);

        return ResponseEntity.accepted().body(changed);
    }

    @GetMapping("/infos/domaines")
    public ResponseEntity<List<String>> getAllDomains()  {


        return ResponseEntity.ok(Arrays.stream(Domaine.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_PROFESSOR','SCOPE_STUDENT')")
    @PutMapping("/project/{name}/edit")
    public ResponseEntity<Boolean> updateProject(@ModelAttribute Project_ToSend project ) throws ProjectException, FilesException, IOException {
        boolean  changed=projectService.updateProject(project);

        return ResponseEntity.accepted().body(changed);
    }

}
