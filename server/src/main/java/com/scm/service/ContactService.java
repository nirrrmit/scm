package com.scm.service;

import com.scm.dto.ContactDTO;
import com.scm.exception.InvalidDataException;
import com.scm.model.entity.Contact;
import com.scm.model.entity.User;
import com.scm.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public List<ContactDTO> findAllByUser(User user) {
        List<Contact> contacts = contactRepository.findByUser(user);
        return contacts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO save(@Valid Contact contact) {
        try {
            return convertToDTO(contactRepository.save(contact));
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Email already exists for this user");
        }
    }

    private ContactDTO convertToDTO(Contact contact) {
        return ContactDTO.builder()
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .phoneNumber(contact.getPhoneNumber())
                .email(contact.getEmail())
                .about(contact.getAbout())
                .favorite(contact.isFavorite())
                .build();
    }
}
