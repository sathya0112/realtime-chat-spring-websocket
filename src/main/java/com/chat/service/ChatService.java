package com.chat.service;

import com.chat.dto.ChatMessage;
import com.chat.dto.MessageDto;
import com.chat.entity.Message;
import com.chat.entity.Room;
import com.chat.entity.User;
import com.chat.repository.MessageRepository;
import com.chat.repository.RoomRepository;
import com.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public MessageDto saveMessage(ChatMessage chatMessage, String username) {
        Room room = roomRepository.findById(chatMessage.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        User sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Message message = new Message();
        message.setRoom(room);
        message.setSender(sender);
        message.setContent(chatMessage.getContent());
        message.setType(chatMessage.getType() != null ? chatMessage.getType() : Message.MessageType.CHAT);
        message.setDeleted(false);
        
        Message savedMessage = messageRepository.save(message);
        
        return convertToDto(savedMessage);
    }
    
    public List<MessageDto> getRoomMessages(Long roomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtDesc(roomId, pageable);
        
        return messages.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<MessageDto> getAllRoomMessages(Long roomId) {
        List<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
        
        return messages.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteMessage(Long messageId, String username) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Only allow deletion by sender or admin/moderator
        if (!message.getSender().getId().equals(user.getId()) && 
            user.getRole() != User.UserRole.ADMIN && 
            user.getRole() != User.UserRole.MODERATOR) {
            throw new RuntimeException("Not authorized to delete this message");
        }
        
        message.setDeleted(true);
        messageRepository.save(message);
    }
    
    private MessageDto convertToDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setRoomId(message.getRoom().getId());
        dto.setSenderId(message.getSender().getId());
        dto.setSenderName(message.getSender().getUsername());
        dto.setContent(message.getContent());
        dto.setType(message.getType());
        dto.setFileUrl(message.getFileUrl());
        dto.setFileName(message.getFileName());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }
}

