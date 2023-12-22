package com.ar.cac.SpringBank.repositories;

import com.ar.cac.SpringBank.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsById(Long id);

    Optional<Account> findByAlias(String alias);

    boolean existsByCbu(String cbu);

    boolean existsByAlias(String alias);

    boolean existsByIdAndAmountGreaterThanEqual(Long id, BigDecimal amount);

    boolean existsByCbuAndIdNot(String cbu, Long id);

    boolean existsByAliasAndIdNot(String alias, Long id);
}
