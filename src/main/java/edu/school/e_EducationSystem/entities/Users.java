package edu.school.e_EducationSystem.entities;

import edu.school.e_EducationSystem.enums.Sexe;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public abstract class Users implements UserDetails, Principal   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Please provide your Last Name")
    private String lastName;
    @NotBlank(message = "Please provide your First Name")
    private String firstName;
    @Email(message = "Please provide a valid email address")
    @NotNull(message = "Please provide your email address")
    @Column(unique = true)
    private String email;
    @Size(min = 10,max=10, message = "Phone Number Incorrect")
    @Column(unique = true)
    @NotNull(message = "Please provide your Phone Number")
    private String phone;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private boolean isExpired=false;
    private boolean isLocked=false;

    private boolean isEnabled=false;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please provide your Sexe")
    private Sexe sexe;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "BirthDay Incorrect")
    private Date birthday;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "professor",fetch = FetchType.EAGER)
    private List<Project> projects=new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Evaluation> evaluations=new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Image profileImage;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role ->
                        new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled;
    }
}
