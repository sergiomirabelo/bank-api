package com.sergio.bankapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sergio.bankapi.model.Account;
import com.sergio.bankapi.repository.AccountRepository;
import com.sergio.bankapi.exception.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {
        account.setBalance(0.0);
        return accountRepository.save(account);
    }

    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));
    }

    public Account deposit(Long id, Double amount) {
        if (amount == null || amount <= 0) {
            throw new BusinessException("Valor deve ser positivo");
        }

        Account account = getAccountById(id);

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Long id, Double amount) {
        if (amount == null || amount <= 0) {
            throw new BusinessException("Valor deve ser positivo");
        }

        Account account = getAccountById(id);

        if (account.getBalance() < amount) {
            throw new BusinessException("Saldo insuficiente");
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    public void transfer(Long fromId, Long toId, Double amount) {

        if (amount == null || amount <= 0) {
            throw new BusinessException("Valor deve ser positivo");
        }

        if (fromId.equals(toId)) {
            throw new BusinessException("Não é possível transferir para a mesma conta");
        }

        Account fromAccount = getAccountById(fromId);
        Account toAccount = getAccountById(toId);

        if (fromAccount.getBalance() < amount) {
            throw new BusinessException("Saldo insuficiente");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}