package com.scm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String about;
    private boolean favorite;
}
