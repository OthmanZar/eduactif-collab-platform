package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.*;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByEmail(String email);
  //  List<Users> findAllByEnabledIsFalse();

}
