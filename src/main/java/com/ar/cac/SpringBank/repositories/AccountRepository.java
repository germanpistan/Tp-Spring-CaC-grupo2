package com.ar.cac.SpringBank.repositories;
import com.ar.cac.SpringBank.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account, Long> {
}
