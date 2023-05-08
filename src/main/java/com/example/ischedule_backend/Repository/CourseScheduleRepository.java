package com.example.ischedule_backend.Repository;

import com.example.ischedule_backend.Model.CourseSchedule;
import com.example.ischedule_backend.Model.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    /*
     * Find all course schedules for a specific course.
     */
    List<CourseSchedule> findByCourseId(int course_id);

    /*
     * Find all course schedules for a specific room.
     */
    List<CourseSchedule> findByRoomId(int room_id);

    /*
     * Find all course schedules for a specific day of the week.
     */
    List<CourseSchedule> findByDayOfWeek(DayOfWeek dayOfWeek);

    /*
     * Find all course schedules that start at or after a specified time.
     */
    List<CourseSchedule> findByStartTimeAfter(Time startTime);

    /*
     * Find all course schedules that end at or before a specified time.
     */
    List<CourseSchedule> findByEndTimeBefore(Time endTime);
}
