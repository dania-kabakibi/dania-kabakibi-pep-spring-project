package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message addNewMessage(Message message) {
        if (!message.getMessageText().isBlank() && message.getMessageText().length() < 256 &&
                accountRepository.existsById(message.getPostedBy())) {
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            return message.get();
        }
        return null;
    }

    public int deleteMessageById(Integer messageId) {
        if (messageId != null && messageRepository.findById(messageId).isPresent()) {
            Message message = messageRepository.getById(messageId);
            messageRepository.deleteById(messageId);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public int updateMessageById(int messageId, String messageText) {
        Optional<Message> oldMessage = messageRepository.findById(messageId);
        if (oldMessage.isPresent() &&
                !messageText.isBlank() && messageText.length() < 256) {
            Message message = oldMessage.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageRepository.findAllMessagesByAccountId(accountId);
    }
}
