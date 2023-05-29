package com.example.ischedule.Model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToMany(mappedBy = "enrolledCourses", cascade=CascadeType.PERSIST)
    private Set<User> enrolledUsers = new HashSet<>();

    /*
     * When the fetch type is set to eager, the associated entities are loaded immediately along with the owning entity.
     * It means that all related entities will be fetched from the database in a single query,
     * reducing the need for additional database access when accessing the associated entities.  */
    @OneToOne(mappedBy = "course", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private CourseSchedule courseSchedule;

    /*
     * This constructor is mandatory for Hibernate to work,
     * since it needs to first fetch the empty objects,
     * to then fill them up with the object data.
     */
    public Course() {}

    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public Set<User> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(Set<User> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    public CourseSchedule getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(CourseSchedule courseSchedule) {
        this.courseSchedule = courseSchedule;
    }
}

