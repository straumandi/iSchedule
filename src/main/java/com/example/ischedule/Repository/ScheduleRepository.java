package com.example.ischedule.Repository;

import com.example.ischedule.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    /*
     * Find the schedule associated with a given user ID.
     */
    Schedule findByUserId(Integer userId);

    /*
     * Find all schedules for a given week number.
     */
    List<Schedule> findByWeekNumber(Integer weekNumber);
}
