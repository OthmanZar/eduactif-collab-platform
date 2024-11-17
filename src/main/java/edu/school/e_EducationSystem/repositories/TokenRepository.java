package edu.school.e_EducationSystem.repositories;

import edu.school.e_EducationSystem.entities.Token;
import edu.school.e_EducationSystem.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    Token findByUser(Users user);
    void deleteAllByUser(Users users);
}
