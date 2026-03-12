package com.chat.service;

import com.chat.dto.CreateRoomRequest;
import com.chat.dto.RoomDto;
import com.chat.entity.Room;
import com.chat.entity.RoomMember;
import com.chat.entity.User;
import com.chat.repository.RoomMemberRepository;
import com.chat.repository.RoomRepository;
import com.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoomMemberRepository roomMemberRepository;
    
    @Transactional
    public RoomDto createRoom(CreateRoomRequest request, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Room room = new Room();
        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setCreator(creator);
        room.setIsPrivate(request.getIsPrivate());
        room.setMaxCapacity(request.getMaxCapacity());
        room.setActive(true);
        
        Room savedRoom = roomRepository.save(room);
        
        // Automatically add creator as a member
        RoomMember member = new RoomMember();
        member.setRoom(savedRoom);
        member.setUser(creator);
        member.setActive(true);
        roomMemberRepository.save(member);
        
        return convertToDto(savedRoom);
    }
    
    public List<RoomDto> getAllPublicRooms() {
        return roomRepository.findAllPublicRooms().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<RoomDto> getUserRooms(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return roomRepository.findRoomsByUserId(user.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public RoomDto getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return convertToDto(room);
    }
    
    @Transactional
    public void joinRoom(Long roomId, String username) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if already a member
        if (roomMemberRepository.existsByRoomIdAndUserId(roomId, user.getId())) {
            throw new RuntimeException("Already a member of this room");
        }
        
        // Check room capacity
        Long currentMembers = roomMemberRepository.countByRoomIdAndActiveTrue(roomId);
        if (currentMembers >= room.getMaxCapacity()) {
            throw new RuntimeException("Room is full");
        }
        
        RoomMember member = new RoomMember();
        member.setRoom(room);
        member.setUser(user);
        member.setActive(true);
        roomMemberRepository.save(member);
    }
    
    @Transactional
    public void leaveRoom(Long roomId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        RoomMember member = roomMemberRepository.findByRoomIdAndUserId(roomId, user.getId())
                .orElseThrow(() -> new RuntimeException("Not a member of this room"));
        
        member.setActive(false);
        roomMemberRepository.save(member);
    }
    
    private RoomDto convertToDto(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setDescription(room.getDescription());
        dto.setCreatorId(room.getCreator().getId());
        dto.setCreatorName(room.getCreator().getUsername());
        dto.setIsPrivate(room.getIsPrivate());
        dto.setMaxCapacity(room.getMaxCapacity());
        dto.setCurrentMembers(roomMemberRepository.countByRoomIdAndActiveTrue(room.getId()).intValue());
        dto.setActive(room.getActive());
        dto.setCreatedAt(room.getCreatedAt());
        return dto;
    }
}

