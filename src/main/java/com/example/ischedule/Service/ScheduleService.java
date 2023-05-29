package com.example.ischedule.Service;

import com.example.ischedule.Model.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule getScheduleById(int scheduleId);
    List<Schedule> getAllSchedules();
    void saveSchedule(Schedule schedule);
    void deleteScheduleById(int scheduleId);
}

