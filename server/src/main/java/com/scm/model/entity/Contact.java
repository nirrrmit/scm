package com.scm.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "contact", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "user_id"})
})
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Email
    @Column(nullable = false)
    private String email;

    private String about;
    private boolean favorite;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Reference to the user who owns this contact
}
