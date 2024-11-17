package edu.school.e_EducationSystem.entities;

import edu.school.e_EducationSystem.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Request {
    @EmbeddedId
    private RequestID id;
    @Size(max = 500)
    @NotBlank(message = "Please provide the purpose")
    private String purpose;
    @NotBlank(message = "Please provide the title")
    private String title;
    private State state=State.Pending;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedAt;

    @MapsId("user")
    @ManyToOne
    private Users user;

    @MapsId("project")
    @ManyToOne
    private Project project;
}
