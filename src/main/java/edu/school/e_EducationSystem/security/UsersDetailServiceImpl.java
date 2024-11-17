package edu.school.e_EducationSystem.security;



import edu.school.e_EducationSystem.repositories.UsersRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UsersDetailServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UsersDetailServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return usersRepository.findByEmail(username)
                .orElseThrow(() ->new UsernameNotFoundException("User Not Found") );
    }
}
