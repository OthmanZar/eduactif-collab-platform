package edu.school.e_EducationSystem.web;

import edu.school.e_EducationSystem.dtos.Request_ToChange;
import edu.school.e_EducationSystem.dtos.Request_ToReturn;
import edu.school.e_EducationSystem.dtos.Request_ToSend;
import edu.school.e_EducationSystem.enums.State;
import edu.school.e_EducationSystem.exceptions.ProjectException;
import edu.school.e_EducationSystem.exceptions.RequestException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.services.IRequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class RequestController {

    private IRequestService requestService;


    @PreAuthorize("hasAnyAuthority('SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @PostMapping("/request")
    public ResponseEntity<Request_ToReturn> addRequest(@RequestBody @Valid Request_ToSend request) throws ProjectException, UserException {
        Request_ToReturn  requestToReturn=requestService.addRequest(request);

        return ResponseEntity.accepted().body(requestToReturn);
    }

    @PreAuthorize("hasAuthority('SCOPE_PROFESSOR')")
    @PatchMapping("/request/state")
    public ResponseEntity<Request_ToReturn> changeState(@RequestBody @Valid Request_ToChange request) throws ProjectException, UserException, RequestException {
        Request_ToReturn requestToReturn=requestService.changeStateOfRequest(request);

        return ResponseEntity.accepted().body(requestToReturn);
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/requests/user/{id}")
    public ResponseEntity<Object> getRequestsOfAnUser(@PathVariable @Valid Integer id) throws UserException {
        List<Request_ToReturn> requestToReturn=requestService.getRequestsOfAnUser(id);

        return ResponseEntity.ok().body(requestToReturn);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/requests/project/{name}")
    public ResponseEntity<List<Request_ToReturn>> getRequestsOfAProject(@PathVariable @Valid String name) throws ProjectException {
        List<Request_ToReturn> requestToReturn = requestService.getRequestsOfAProject(name);

        return ResponseEntity.ok().body(requestToReturn);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/requests")
    public ResponseEntity<Object> getAllRequest(){
        List<Request_ToReturn> requestToReturn = requestService.getAllRequest();

        return ResponseEntity.ok().body(requestToReturn);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_STUDENT','SCOPE_PROFESSOR')")
    @GetMapping("/requests/state/{state}")
    public ResponseEntity<Object> getAllRequestsWithAState(@PathVariable @Valid State state){
        List<Request_ToReturn> requestToReturn=requestService.getAllRequestsWithAState(state);

        return ResponseEntity.ok().body(requestToReturn);
    }

}
