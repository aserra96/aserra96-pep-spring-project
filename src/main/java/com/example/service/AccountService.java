package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.exception.CustomException.BadRequestException;
import com.example.exception.CustomException.ConflictException;
import com.example.exception.CustomException.UnauthorizedException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new BadRequestException("Username Cannot Be Blank");
        }
        if (account.getUsername() == null || account.getPassword().length() < 4) {
            throw new BadRequestException("Username Cannot Be Less Than 4 Characters Long");
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new ConflictException("User Exists");
        }
        return accountRepository.save(account);
    }

    public Account login (String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account == null || !account.getPassword().equals(password)) {
            throw new UnauthorizedException("invalid Username or password");
        }
        return account;
    }
}
