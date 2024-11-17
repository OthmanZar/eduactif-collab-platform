package edu.school.e_EducationSystem.mappers;

import edu.school.e_EducationSystem.dtos.Admin_ToSend;
import edu.school.e_EducationSystem.entities.Administrator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminMapper {

    private PasswordEncoder passwordEncoder;

    public AdminMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Administrator toAdmin(Admin_ToSend adminToSend){
        Administrator administrator = new Administrator();
        administrator.setBirthday(adminToSend.birthday());
        administrator.setEmail(adminToSend.email());
        administrator.setEnabled(true);
        administrator.setFirstName(adminToSend.firstName());
        administrator.setLastName(adminToSend.lastName());
        administrator.setPhone(adminToSend.phone());
        administrator.setSexe(adminToSend.sexe());
        administrator.setPassword(passwordEncoder.encode(adminToSend.password()));

        return administrator;
    }

}
