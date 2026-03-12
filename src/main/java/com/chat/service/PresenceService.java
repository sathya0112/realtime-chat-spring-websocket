package com.chat.service;

import com.chat.dto.UserDto;
import com.chat.entity.RoomMember;
import com.chat.entity.User;
import com.chat.repository.RoomMemberRepository;
import com.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PresenceService {

    // In-memory storage (fallback when Redis is not available)
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();
    private final Map<Long, Set<String>> roomOnlineUsers = new ConcurrentHashMap<>();

    @Autowired
    private RoomMemberRepository roomMemberRepository;

    @Autowired
    private UserRepository userRepository;
    
    public void markUserOnline(String username) {
        onlineUsers.add(username);
    }

    public void markUserOffline(String username) {
        onlineUsers.remove(username);
        // Remove from all rooms
        roomOnlineUsers.values().forEach(users -> users.remove(username));
    }

    public void addUserToRoom(Long roomId, String username) {
        roomOnlineUsers.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(username);
        markUserOnline(username);
    }

    public void removeUserFromRoom(Long roomId, String username) {
        Set<String> users = roomOnlineUsers.get(roomId);
        if (users != null) {
            users.remove(username);
        }
    }

    public List<UserDto> getOnlineUsersInRoom(Long roomId) {
        Set<String> onlineUsernames = roomOnlineUsers.getOrDefault(roomId, Collections.emptySet());

        if (onlineUsernames.isEmpty()) {
            return List.of();
        }

        return onlineUsernames.stream()
                .map(username -> {
                    User user = userRepository.findByUsername(username).orElse(null);
                    if (user != null) {
                        UserDto dto = new UserDto();
                        dto.setId(user.getId());
                        dto.setUsername(user.getUsername());
                        dto.setFullName(user.getFullName());
                        dto.setEmail(user.getEmail());
                        dto.setRole(user.getRole().name());
                        dto.setOnline(true);
                        return dto;
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    public boolean isUserOnline(String username) {
        return onlineUsers.contains(username);
    }
    
    public List<UserDto> getRoomMembers(Long roomId) {
        List<RoomMember> members = roomMemberRepository.findActiveMembers(roomId);
        
        return members.stream()
                .map(member -> {
                    User user = member.getUser();
                    UserDto dto = new UserDto();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole().name());
                    dto.setOnline(isUserOnline(user.getUsername()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

