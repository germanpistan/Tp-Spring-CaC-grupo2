package com.ar.cac.SpringBank.entities;

import com.ar.cac.SpringBank.records.user.NewUserRecord;
import com.ar.cac.SpringBank.records.user.UpdateUserRecord;
import com.ar.cac.SpringBank.records.user.UserRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "document", unique = true)
    private String document;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_enabled", nullable = false)
    boolean isEnabled = true;


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<Account> accounts = new ArrayList<>();

    public User(UserRecord record) {

        this.id = record.id();
        this.lastName = record.lastName();
        this.firstName = record.firstName();
        this.email = record.email();
        this.password = record.password();
        this.document = record.document();
        this.birthDate = record.birthDate();
        this.address = record.address();
    }

    public User(NewUserRecord record) {

        this.lastName = record.lastName();
        this.firstName = record.firstName();
        this.email = record.email();
        this.password = record.password();
        this.document = record.document();
        this.birthDate = record.birthDate();
        this.address = record.address();
    }

    public void update(UpdateUserRecord record) {

        if (record.lastName() != null) this.lastName = record.lastName();
        if (record.firstName() != null) this.firstName = record.firstName();
        if (record.email() != null) this.email = record.email();
        if (record.password() != null) this.password = record.password();
        if (record.document() != null) this.document = record.document();
        if (record.birthDate() != null) this.birthDate = record.birthDate();
        if (record.address() != null) this.address = record.address();
    }

    public void disabled() {

        // En los bancos no se restablecen los usuarios?.
        this.isEnabled = !this.isEnabled;
    }
}
