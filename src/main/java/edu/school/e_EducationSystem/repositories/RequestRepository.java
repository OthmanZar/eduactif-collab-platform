package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.Project;
import edu.school.e_EducationSystem.entities.Request;
import edu.school.e_EducationSystem.entities.RequestID;
import edu.school.e_EducationSystem.entities.Users;
import edu.school.e_EducationSystem.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, RequestID> {

    List<Request> findAllByUser(Users user);
    List<Request> findAllByProject(Project project);
    List<Request> findAllByState(State state);


}
