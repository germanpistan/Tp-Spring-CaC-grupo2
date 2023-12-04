package com.ar.cac.SpringBank.repositories;

import com.ar.cac.SpringBank.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
