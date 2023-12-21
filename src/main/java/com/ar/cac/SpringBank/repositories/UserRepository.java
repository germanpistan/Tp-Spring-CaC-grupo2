package com.ar.cac.SpringBank.repositories;


import com.ar.cac.SpringBank.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByDocument(String document);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByDocumentAndIdNot(String document, Long id);
}
