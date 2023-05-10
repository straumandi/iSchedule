package com.example.ischedule.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "week_number")
    private int weekNumber;

    /*
     * The `CascadeType.ALL` property on the `@OneToMany` annotation means that when a `Schedule` entity
     * is persisted, updated, or deleted, the corresponding `CourseSchedule` entities in the `courseSchedules`
     * list will also be persisted, updated, or deleted automatically. This saves you from having to manually
     * manage the persistence of the related entities and ensures data consistency.
     */
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<CourseSchedule> courseSchedules = new ArrayList<>();

    /*
     * This constructor is mandatory for Hibernate to work,
     * since it needs to first fetch the empty objects,
     * to then fill them up with the object data.
     */
    public Schedule() {}

    public Schedule(User user, int weekNumber, List<CourseSchedule> courseSchedules) {
        this.user = user;
        this.weekNumber = weekNumber;
        this.courseSchedules = courseSchedules;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public List<CourseSchedule> getCourseSchedules() {
        return courseSchedules;
    }

    public void setCourseSchedules(List<CourseSchedule> courseSchedules) {
        this.courseSchedules = courseSchedules;
    }
}


