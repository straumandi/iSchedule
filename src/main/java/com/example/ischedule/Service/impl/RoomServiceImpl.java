package com.example.ischedule.Service.impl;

import com.example.ischedule.Model.CourseSchedule;
import com.example.ischedule.Model.Room;
import com.example.ischedule.Repository.CourseScheduleRepository;
import com.example.ischedule.Repository.RoomRepository;
import com.example.ischedule.Service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    public RoomServiceImpl(RoomRepository roomRepository, CourseScheduleRepository courseScheduleRepository) {
        this.roomRepository = roomRepository;
        this.courseScheduleRepository = courseScheduleRepository;
    }

    @Override
    public Room getRoomById(int roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        return roomOptional.orElse(null);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public void deleteRoom(int roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

        // Update the associated CourseSchedule entities
        List<CourseSchedule> schedulesToUpdate = courseScheduleRepository.findByRoomId(room.getId());
        for (CourseSchedule schedule : schedulesToUpdate) {
            schedule.setRoom(null);
        }

        // Delete the room
        roomRepository.delete(room);
    }
}
