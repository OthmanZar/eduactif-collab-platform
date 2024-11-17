package edu.school.e_EducationSystem;


import edu.school.e_EducationSystem.dtos.Admin_ToSend;
import edu.school.e_EducationSystem.dtos.Professor_ToSend;
import edu.school.e_EducationSystem.dtos.SchoolYear_ToReturn;
import edu.school.e_EducationSystem.dtos.StudentDTO_ToSend;

import edu.school.e_EducationSystem.entities.Role;
import edu.school.e_EducationSystem.enums.Departement;
import edu.school.e_EducationSystem.enums.EmailTemplateName;
import edu.school.e_EducationSystem.enums.Sexe;

import edu.school.e_EducationSystem.enums.Type;
import edu.school.e_EducationSystem.exceptions.MyAuthenticationException;
import edu.school.e_EducationSystem.repositories.RoleRepository;
import edu.school.e_EducationSystem.repositories.UsersRepository;
import edu.school.e_EducationSystem.security.RsaKeysConfig;
import edu.school.e_EducationSystem.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(RsaKeysConfig.class)
@EnableAsync
public class EEducationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EEducationSystemApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


		//@Bean
	CommandLineRunner commandLineRunner55(IAdministratorService administratorService,RoleRepository roleRepository) throws MyAuthenticationException {

		return args -> {

			Role role =new Role();
			role.setName("PROFESSOR");
			role.setCreatedAt(LocalDateTime.now());
			roleRepository.save(role);

			Role role2 = new Role();
			role2.setName("ADMIN");
			role2.setCreatedAt(LocalDateTime.now());
			roleRepository.save(role2);

			Role role3 = new Role();
			role3.setName("STUDENT");
			role3.setCreatedAt(LocalDateTime.now());
			roleRepository.save(role3);

			administratorService.addAdmin(new Admin_ToSend(
					"Zarrouk",
					"Othmane",
					"othmanzarrouk30@gmail.com",
					"0649503801",
					"azaz1212",
					Sexe.Men,
					new Date()
			));
		};


	}




	//	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository, ISchoolYearService iSchoolYearService , IProfessorService professorService,
										IStudentSevice studentSevice, AuthenticationServiceImpl auth, UsersRepository usersRepository) {
	return args-> {
		Role role =new Role();
		role.setName("PROFESSOR");
		role.setCreatedAt(LocalDateTime.now());
	    Role role1 = roleRepository.save(role);
//		iSchoolYearService.addSchoolYear(new SchoolYear_ToReturn(
//				"Informatique Et Telecom"
//				, Type.Master,30,
//				12,
//				Departement.AI_MachineLearning));
//
//		StudentDTO_ToSend student = new StudentDTO_ToSend(
//				"Hajji",
//				"Aymane",
//				"salahlaatabiya@gmail.com",
//				"0625317956",
//				passwordEncoder().encode("azaz1212") ,
//				Sexe.Men,
//				new Date(),
//				"Informatique Et Telecom"
//
//		);
//		StudentDTO_ToSend student1 = new StudentDTO_ToSend(
//				"Hajji",
//				"Aymane",
//				"dfdjdswcdrs2ds@gmail.com",
//				"0663850956",
//				passwordEncoder().encode("azaz1212") ,
//				Sexe.Men,
//				new Date(),
//				"Informatique Et Telecom"
//
//		);
//		StudentDTO_ToSend student2 = new StudentDTO_ToSend(
//				"Hajji",
//				"Aymane",
//				"dfdjdswcdrsqsd2ds@gmail.com",
//				"0663854956",
//				passwordEncoder().encode("azaz1212") ,
//				Sexe.Men,
//				new Date(),
//				"Informatique Et Telecom"
//
//		);
//
//		studentSevice.addStudent(student);
//		studentSevice.addStudent(student1);
//		studentSevice.addStudent(student2);
//				professorService.addProfessor(new Professor_ToSend(
//				"Zarrouk",
//				"Othmane",
//				"othmanze@gmail.com",
//				"0649503801",
//				"azaz1212",
//				Sexe.Men,
//				new Date(),
//				Departement.FullStackDevelopement,
//						"image"
//		));

		//auth.sendValidationEmail(usersRepository.findById(1).orElseThrow());
	};

//
//		professorService.addProfessor(new Professor_ToSend(
//				"Omar",
//				"Othmane",
//				"omarze@gmail.com",
//				"0650164846",
//				"azaz1212",
//				Sexe.Men,
//				new Date(),
//				Departement.BackendDevelopemet
//		));
	}


//	@Bean
	CommandLineRunner commandLineRunner2(IProfessorService professorService,
										 ICourService courService, ISchoolYearService service, SchoolYearServiceImpl schoolYearServiceImpl){
		return args ->{

//			courService.addCours(new Cours_ToSend(
//					"Java OOP",
//					3
//			));
//			courService.addCours(new Cours_ToSend(
//					"Script Linux",
//					3
//			));
//			courService.addCours(new Cours_ToSend(
//					"Graph Theorie",
//					4
//			));
//			courService.addCours(new Cours_ToSend(
//					"Web Developpement",
//					4
//			));
//			service.addSchoolYear(new SchoolYear_ToReturn("CryptoGraphy",
//					Type.Master,
//					2,
//					Departement.FrontDevelopement));
//			String[] courses = {"Web Developpement","Graph Theorie","Script Linux"};
//			System.out.println(
//					schoolYearServiceImpl.
//							addCoursesToSchoolYear(courses,"Informatique Et Telecom"));

		};
	}





	}

