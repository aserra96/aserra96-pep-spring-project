package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.CustomException.BadRequestException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage (Message message) {
        if (message.getMessageText() == null || message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            throw new BadRequestException("Message is invalid");
        }
        if (accountRepository.findById(Long.valueOf(message.getPostedBy())).isEmpty()) {
            throw new BadRequestException("User does not exist");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    public int deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public int updateMessage(Integer messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255) {
            throw new BadRequestException("Message is Invalid");
        }
        Optional<Message> exisitngMessageOpt = messageRepository.findById(messageId);
        if (exisitngMessageOpt.isPresent()) {
            Message message = exisitngMessageOpt.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return 1;
        } else {
            throw new BadRequestException("ID not found");
        }
    }

    public List<Message> getAllMessagesByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
