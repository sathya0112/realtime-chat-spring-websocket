package com.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    
    private Long id;
    private String name;
    private String description;
    private Long creatorId;
    private String creatorName;
    private Boolean isPrivate;
    private Integer maxCapacity;
    private Integer currentMembers;
    private Boolean active;
    private LocalDateTime createdAt;
}

