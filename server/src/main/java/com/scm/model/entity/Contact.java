package com.scm.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String about;
    private boolean favorite;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Reference to the user who owns this contact
}
