package com.example.ischedule.Service;

import com.example.ischedule.Model.Room;

import java.util.List;

public interface RoomService {
    Room getRoomById(int roomId);

    List<Room> getAllRooms();
}
