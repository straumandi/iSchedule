package com.example.ischedule.Service.impl;

import com.example.ischedule.Model.Schedule;
import com.example.ischedule.Repository.ScheduleRepository;
import com.example.ischedule.Service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule getScheduleById(int scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule ID: " + scheduleId));
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteScheduleById(int scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}

