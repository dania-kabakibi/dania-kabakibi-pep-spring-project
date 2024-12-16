package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addNewAccount(Account account) {
        if (!account.getUsername().isBlank() && account.getPassword().length() > 3) {
            if (accountRepository.findByUsername(account.getUsername()).isEmpty())
                return accountRepository.save(account);
        }
        return null;
    }

    public Account userLogin(Account account) {
        Optional<Account> account2 = accountRepository.findByUsername(account.getUsername());
        if (account2.isPresent()) {
            if (account2.get().getPassword().equals(account.getPassword())) {
                return account2.get();
            }
        }
        return null;
    }

}