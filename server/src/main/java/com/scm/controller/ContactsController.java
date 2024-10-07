package com.scm.controller;

import com.scm.model.entity.Contact;
import com.scm.service.ContactService;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {

//    @Autowired
//    private ContactService contactService;
//
//    @GetMapping
//    public ResponseEntity<List<Contact>> getAllContacts(@AuthenticationPrincipal UserPrincipal userPrincipal) {
//        // Logic to retrieve all contacts for the logged-in user
//        Long userId = userPrincipal
//        List<Contact> contacts = contactService.getContactsByUser();
//        return ResponseEntity.ok(contacts);
//    }
//
//    @PostMapping
//    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
//        // Logic to add a new contact
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Contact> getContact(@PathVariable Long id) {
//        // Logic to retrieve a specific contact by ID
//        return ResponseEntity.ok(contact);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contact) {
//        // Logic to update an existing contact
//        return ResponseEntity.ok(updatedContact);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
//        // Logic to delete a contact by ID
//        return ResponseEntity.noContent().build();
//    }
}
