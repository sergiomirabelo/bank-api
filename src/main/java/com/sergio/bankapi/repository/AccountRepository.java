package com.sergio.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sergio.bankapi.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}