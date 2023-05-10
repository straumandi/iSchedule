package com.example.ischedule.Model;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "preferences")
public class Preferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "preferred_start_time")
    private Time preferredStartTime;

    @Column(name = "preferred_end_time")
    private Time preferredEndTime;

    @ManyToOne
    @JoinColumn(name = "preferred_room_id")
    private Room preferredRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "prefered_day_of_week")
    private DayOfWeek preferedDayOfWeek;

    /*
     * This constructor is mandatory for Hibernate to work,
     * since it needs to first fetch the empty objects,
     * to then fill them up with the object data.
     */
    public Preferences() {}

    public Preferences(int id, User user, Time preferredStartTime, Time preferredEndTime, Room preferredRoom, DayOfWeek preferedDayOfWeek) {
        this.id = id;
        this.user = user;
        this.preferredStartTime = preferredStartTime;
        this.preferredEndTime = preferredEndTime;
        this.preferredRoom = preferredRoom;
        this.preferedDayOfWeek = preferedDayOfWeek;
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

    public Time getPreferredStartTime() {
        return preferredStartTime;
    }

    public void setPreferredStartTime(Time preferredStartTime) {
        this.preferredStartTime = preferredStartTime;
    }

    public Time getPreferredEndTime() {
        return preferredEndTime;
    }

    public void setPreferredEndTime(Time preferredEndTime) {
        this.preferredEndTime = preferredEndTime;
    }

    public Room getPreferredRoom() {
        return preferredRoom;
    }

    public void setPreferredRoom(Room preferredRoom) {
        this.preferredRoom = preferredRoom;
    }

    public DayOfWeek getPreferedDayOfWeek() {
        return preferedDayOfWeek;
    }

    public void setPreferedDayOfWeek(DayOfWeek preferedDayOfWeek) {
        this.preferedDayOfWeek = preferedDayOfWeek;
    }
}


