package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.entities.Professor;
import edu.school.e_EducationSystem.entities.Student;
import edu.school.e_EducationSystem.entities.Token;
import edu.school.e_EducationSystem.entities.Users;
import edu.school.e_EducationSystem.enums.EmailTemplateName;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.exceptions.UserException;
import edu.school.e_EducationSystem.repositories.ProfessorRepository;
import edu.school.e_EducationSystem.repositories.StudentRepository;
import edu.school.e_EducationSystem.repositories.TokenRepository;
import edu.school.e_EducationSystem.repositories.UsersRepository;
import edu.school.e_EducationSystem.security.UsersDetailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UsersDetailServiceImpl usersDetailServiceImpl;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final UsersRepository usersRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    @Value("${application.mailing.frontend.application-url}")
    private String activationUrl;

    @Override
    public Map<String, String> login(String userName, String password, boolean withRefreshToken, String granType, String refreshToken) throws MyAuthenticationException {


        Map<String, String> tokens = new HashMap<>();
        String subject;
        String claims;

        if(granType.equals("password")){
                if(password ==null && userName !=null ){
                    throw new MyAuthenticationException("password is missing");
                } else if (password !=null && userName ==null){
                    throw new MyAuthenticationException("userName is missing");
                }else if(password == null && userName==null) {
                    throw new MyAuthenticationException("userName and password are missing");
                }

            Authentication authentication = authenticateWithPassword(userName,password);
                if (authentication==null){
                    throw new UsernameNotFoundException("Username Or Password incorrect");
                }
            subject=authentication.getName();
            claims=authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            if(!withRefreshToken){
                int expireAt=50;
                tokens.put("accessToken",createAccessToken(subject,claims,expireAt));
            }else{
                int accessExpireAt=10;
                int refreshExpireAt=50;
                tokens.put("accessToken",createAccessToken(subject,claims,accessExpireAt));
                tokens.put("refreshToken",createAccessToken(subject,claims,refreshExpireAt));

            }
        } else if (granType.equals("refresh")) {
            if(refreshToken==null){
                throw new MyAuthenticationException("refreshToken is Null");
            }
            Jwt decode = null;
            try {
                decode = jwtDecoder.decode(refreshToken);
            } catch (JwtException e) {
                String message = e.getMessage();
                String[] parts = message.split(":");
                throw new MyAuthenticationException(parts[1].trim());
            }
            subject = decode.getSubject();
            UserDetails userDetails = usersDetailServiceImpl.loadUserByUsername(subject);
            claims = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));

            int accessExpireAt=10;
            String newAccessToken = createAccessToken(subject,claims,accessExpireAt);
            tokens.put("accessToken",newAccessToken);

        }else{
            throw new MyAuthenticationException("grantType is Null or Incorrect , must be password or refresh");
        }

        System.out.println(claims);
        System.out.println(claims.contains("ADMIN"));


        return tokens;
    }

    @Override
    public Authentication authenticateWithPassword(String userName, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName, password
                ));
    }

    @Override
    public String createAccessToken(String subject, String claims, int expiresAtMinutes) {

        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(expiresAtMinutes, ChronoUnit.MINUTES))
                .issuer("security-service")
                .claim("scope",claims).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    @Override
    public String createRefreshToken(String subject, int expiresAtMinutes) {

        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(expiresAtMinutes, ChronoUnit.MINUTES))
                .issuer("security-service")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
    }



    public void sendValidationEmail(Users users) throws MessagingException {
        var newToken = generateAndSentActivationToken(users);
        emailService.sendEmail(
                users.getEmail(),
                users.getFirstName()+" "+users.getLastName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }


    public String generateAndSentActivationToken(Users users) {
        String generatedToken = generateActivationCode(6);
        Token token = new Token();
        token.setUser(users);
        token.setToken(generatedToken);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(token);

        return generatedToken;
    }

    public String generateActivationCode(int lenght) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0;i<lenght;i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
    return codeBuilder.toString();
    }



    public String validateUser(String email,String code,String type) throws UserException, MessagingException, MyAuthenticationException {
      //  Token token = tokenRepository.fin
        Users users =null;

        if(type.equals("student")){
            users= studentRepository.findByEmail(email);
        } else if (type.equals("teacher")) {
            users =  professorRepository.findByEmail(email);
        }


        if(users==null){
            throw new UserException("User Not Found");
        }

       if (users.isEnabled()){
           throw new UserException("User Already Enabled");
       }

        Token token = tokenRepository.findByUser(users);

        if(token==null){
            throw new UserException("Invalid Code");
        }

        if(token.getExpiresAt().isBefore(LocalDateTime.now())){
            tokenRepository.delete(token);
            sendValidationEmail(users);
            throw new MyAuthenticationException("Expired , Another Validation Code has been sent") ;
        }else {
            if(token.getToken().equals(code)){
                users.setEnabled(true);
                token.setValidatedAt(LocalDateTime.now());
                usersRepository.save(users);
                tokenRepository.save(token);
            }else{
                throw new MyAuthenticationException("Invalid Token") ;

            }



            return "User Enabled Successfully";
        }


    }
}
