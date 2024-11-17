package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface IAuthenticationService {

    Map<String,String> login(String userName,
                             String password,
                             boolean withRefreshToken,
                             String granType,
                             String refreshToken) throws MyAuthenticationException;

    Authentication authenticateWithPassword(String userName,
                                            String password);


    String createAccessToken(String subject, String claims,int expiresAtMinutes);

    String createRefreshToken(String subject,int expiresAtMinutes);



}
