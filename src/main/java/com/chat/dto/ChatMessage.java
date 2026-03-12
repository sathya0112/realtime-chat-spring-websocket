package com.chat.dto;

import com.chat.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    
    private Long roomId;
    private String content;
    private Message.MessageType type;
    private String senderName;
    private Long senderId;
}

