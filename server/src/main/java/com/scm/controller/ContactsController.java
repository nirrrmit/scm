package com.scm.controller;

import com.scm.config.CurrentUser;
import com.scm.dto.ContactDTO;
import com.scm.model.entity.Contact;
import com.scm.model.entity.User;
import com.scm.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts(@CurrentUser User user) {
        List<ContactDTO> contacts = contactService.findAllByUser(user);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@CurrentUser User user, @Valid @RequestBody Contact contact) {
        contact.setUser(user);
        ContactDTO savedContact = contactService.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }
}
