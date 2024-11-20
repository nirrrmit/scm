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

    @PostMapping("/add")
    public ResponseEntity<ContactDTO> addContact(@CurrentUser User user, @Valid @RequestBody Contact contact) {
        contact.setUser(user);
        ContactDTO savedContact = contactService.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<ContactDTO> updateContact(
            @CurrentUser User user,
            @PathVariable Long contactId,
            @Valid @RequestBody Contact updatedContact) {

        // Ensure the contact belongs to the current user
        Contact existingContact = contactService.findByIdAndUser(contactId, user);
        if (existingContact == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Update contact details
        existingContact.setFirstName(updatedContact.getFirstName());
        existingContact.setLastName(updatedContact.getLastName());
        existingContact.setPhoneNumber(updatedContact.getPhoneNumber());
        existingContact.setEmail(updatedContact.getEmail());
        existingContact.setAbout(updatedContact.getAbout());
        existingContact.setFavorite(updatedContact.isFavorite());

        ContactDTO savedContact = contactService.save(existingContact);
        return ResponseEntity.ok(savedContact);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@CurrentUser User user, @PathVariable Long contactId) {
        // Ensure the contact belongs to the current user
        Contact existingContact = contactService.findByIdAndUser(contactId, user);
        if (existingContact == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        contactService.delete(existingContact);
        return ResponseEntity.noContent().build();
    }
}
