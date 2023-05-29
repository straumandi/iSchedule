package com.example.ischedule.Repository;

import com.example.ischedule.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    /*
     * Find all rooms with a capacity greater than or equal to a specified number
     */
    List<Room> findByCapacityGreaterThanEqual(Integer capacity);
}
