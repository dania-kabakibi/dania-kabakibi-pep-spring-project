package com.example.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account) {
        Account newAccount = accountService.addNewAccount(account);
        if (newAccount == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(newAccount);
        }
        return ResponseEntity.status(HttpStatus.OK).body(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> userLogin(@RequestBody Account account) {
        Account existingAccount = accountService.userLogin(account);
        if (existingAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(existingAccount);
        }
        return ResponseEntity.status(HttpStatus.OK).body(existingAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        Message newMessage = messageService.addNewMessage(message);
        if (newMessage != null) {
            return ResponseEntity.status(HttpStatus.OK).body(newMessage);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newMessage);
    }

    @GetMapping("/messages")
    public List<Message> retrieveAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> retrieveMessageById(@PathVariable int messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        if (messageService.deleteMessageById(messageId) == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId,
            @RequestBody HashMap<String, String> body) {
        if (messageService.updateMessageById(messageId, body.get("messageText")) == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> retrieveAllMessagesForUserTest(@PathVariable int accountId) {
        return messageService.getAllMessagesByAccountId(accountId);
    }

}