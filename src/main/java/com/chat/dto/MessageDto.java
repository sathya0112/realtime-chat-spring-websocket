package com.chat.dto;

import com.chat.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    
    private Long id;
    private Long roomId;
    private Long senderId;
    private String senderName;
    private String content;
    private Message.MessageType type;
    private String fileUrl;
    private String fileName;
    private LocalDateTime createdAt;
}

