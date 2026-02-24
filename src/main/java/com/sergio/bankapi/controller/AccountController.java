package com.sergio.bankapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.sergio.bankapi.model.Account;
import com.sergio.bankapi.service.AccountService;
import com.sergio.bankapi.dto.TransferRequest;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping
    public List<Account> listAccounts() {
        return accountService.listAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestParam Double amount) {
        return accountService.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestParam Double amount) {
        return accountService.withdraw(id, amount);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest request) {

        accountService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );

        return "Transfer completed successfully";
    }
}