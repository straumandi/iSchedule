CREATE
DATABASE ischedule;

USE
ischedule;

CREATE TABLE users
(
    id       INT          NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     ENUM('STUDENT', 'ADMIN', 'ASSISTANT') NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (email)
);

CREATE TABLE courses
(
    id          INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(1000) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE rooms
(
    id       INT          NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    capacity INT DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE schedules
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT NOT NULL,
    week_number INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE course_schedule
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    course_id   INT  NOT NULL,
    schedule_id INT  NOT NULL,
    day_of_week ENUM('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN') NOT NULL,
    start_time  TIME NOT NULL,
    end_time    TIME NOT NULL,
    room_id     INT  NOT NULL,
    CONSTRAINT unique_course_schedule UNIQUE (course_id, schedule_id, day_of_week, start_time),
    FOREIGN KEY (course_id) REFERENCES courses (id),
    FOREIGN KEY (schedule_id) REFERENCES schedules (id),
    FOREIGN KEY (room_id) REFERENCES rooms (id)
);

CREATE TABLE preferences
(
    id                   INT PRIMARY KEY AUTO_INCREMENT,
    user_id              INT,
    preferred_start_time TIME NOT NULL,
    preferred_end_time   TIME NOT NULL,
    preferred_room_id    INT,
    prefered_day_of_week ENUM('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'),
    CONSTRAINT fk_user_id_for_preferences FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_preferred_room_id FOREIGN KEY (preferred_room_id) REFERENCES rooms (id)
)
    -- create bulk data --
-- Insert sample courses
    INSERT INTO courses (description, name, admin_id, instructor_id) VALUES
('Course 1 description', 'Course 1', 1, 1),
('Course 2 description', 'Course 2', 1, 2),
('Course 3 description', 'Course 3', 1, 1);

-- Insert sample rooms
INSERT INTO rooms (capacity, name)
VALUES (30, 'Room 101'),
       (25, 'Room 102'),
       (20, 'Room 103');

-- Insert sample schedules
INSERT INTO schedules (week_number, user_id)
VALUES (1, 1),
       (2, 1),
       (1, 6);

-- Insert sample course schedules
INSERT INTO course_schedule (day_of_week, end_time, start_time, course_id, room_id, schedule_id)
VALUES ('Monday', '10:00:00', '09:00:00', 1, 1, 1),
       ('Wednesday', '12:00:00', '11:00:00', 1, 1, 2),
       ('Friday', '14:00:00', '13:00:00', 2, 2, 1),
       ('Tuesday', '16:00:00', '15:00:00', 3, 3, 3);

-- Insert sample user-course enrollments
INSERT INTO user_course (user_id, course_id, id)
VALUES (1, 1, 1),
       (1, 2, 2),
       (6, 1, 3),
       (7, 3, 4);

-- Insert sample preferences
INSERT INTO preferences (prefered_day_of_week, preferred_end_time, preferred_start_time, preferred_room_id, user_id)
VALUES ('Monday', '12:00:00', '10:00:00', 1, 1),
       ('Friday', '16:00:00', '14:00:00', 2, 1);
