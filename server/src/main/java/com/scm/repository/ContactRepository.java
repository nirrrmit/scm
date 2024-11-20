package com.scm.repository;

import com.scm.model.entity.Contact;
import com.scm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUser(User user);

    Optional<Contact> findByIdAndUser(Long id, User user);

}

