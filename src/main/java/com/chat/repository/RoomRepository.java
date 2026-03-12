package com.chat.repository;

import com.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    List<Room> findByActiveTrue();
    
    List<Room> findByCreatorId(Long creatorId);
    
    @Query("SELECT r FROM Room r WHERE r.active = true AND r.isPrivate = false")
    List<Room> findAllPublicRooms();
    
    @Query("SELECT r FROM Room r JOIN r.members m WHERE m.user.id = :userId AND r.active = true")
    List<Room> findRoomsByUserId(@Param("userId") Long userId);
}

