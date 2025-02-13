package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.CustomException;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.registerAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
        } catch (CustomException.ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (CustomException.BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account loggedIn = accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(loggedIn);
        } catch (CustomException.UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
        } catch (CustomException.BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } 
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") Integer messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable("messageId") Integer messageId) {
        int rowDelete = messageService.deleteMessage(messageId);
        if (rowDelete == 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok(rowDelete);
        }
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessage(@PathVariable("message_id") Integer messageId, @RequestBody Map<String, String> requestBody) {
        String newMessageText = requestBody.get("message_text");

        try {
            int rowUpdated = messageService.updateMessage(messageId, newMessageText);
            return ResponseEntity.ok(rowUpdated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/accounts/{account_id}/messages") 
    public ResponseEntity<List<Message>> getAllMessagesForUser(@PathVariable("account_id") Integer accountId) {
        List<Message> messages = messageService.getAllMessagesByUser(accountId);
        return ResponseEntity.ok(messages);
    }
}
