CREATE
    DATABASE ischedule;

USE
    ischedule;

CREATE TABLE users
(
    id       INT                                    NOT NULL AUTO_INCREMENT,
    username VARCHAR(255)                           NOT NULL,
    email    VARCHAR(255)                           NOT NULL,
    password VARCHAR(255)                           NOT NULL,
    role     ENUM ('STUDENT', 'ADMIN', 'ASSISTANT') NOT NULL,
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
    course_id   INT                                                    NOT NULL,
    schedule_id INT                                                    NOT NULL,
    day_of_week ENUM ('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN') NOT NULL,
    start_time  TIME                                                   NOT NULL,
    end_time    TIME                                                   NOT NULL,
    room_id     INT                                                    NOT NULL,
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
    prefered_day_of_week ENUM ('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'),
    CONSTRAINT fk_user_id_for_preferences FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_preferred_room_id FOREIGN KEY (preferred_room_id) REFERENCES rooms (id)
);


-- create bulk data --


-- Insert sample data into the 'users' table
INSERT INTO `users` (`id`, `email`, `password`, `role`, `username`)
VALUES (1, 'john@example.com', '$2y$12$qZBG.TiVcPJrWW7wZL2zOu988sfqASQ2BAfk2mEkb1FkwnzJSzm2W', 'STUDENT', 'john'),
       (2, 'emma@example.com', '$2y$12$qZBG.TiVcPJrWW7wZL2zOu988sfqASQ2BAfk2mEkb1FkwnzJSzm2W', 'STUDENT', 'emma'),
       (3, 'admin@example.com', '$2y$12$qZBG.TiVcPJrWW7wZL2zOu988sfqASQ2BAfk2mEkb1FkwnzJSzm2W', 'ADMIN', 'admin');

-- Insert sample data into the 'courses' table
INSERT INTO `courses` (`id`, `description`, `name`, `admin_id`, `instructor_id`)
VALUES (1, 'Introduction to Programming', 'Programming 101', 3, 1),
       (2, 'Web Development Basics', 'Web Dev 101', 3, 2),
       (3, 'Database Management', 'DBMS', 3, 1);

-- Insert sample data into the 'rooms' table
INSERT INTO `rooms` (`id`, `capacity`, `name`)
VALUES (1, 30, 'Room A'),
       (2, 25, 'Room B'),
       (3, 40, 'Room C');

-- Insert sample data into the 'schedules' table
INSERT INTO `schedules` (`id`, `week_number`, `user_id`)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 1);

-- Insert sample data into the 'course_schedule' table
INSERT INTO `course_schedule` (`id`, `day_of_week`, `end_time`, `start_time`, `course_id`, `room_id`, `schedule_id`)
VALUES (1, 'Monday', '12:00:00', '10:00:00', 1, 1, 1),
       (2, 'Wednesday', '14:00:00', '12:00:00', 2, 2, 2),
       (3, 'Friday', '16:00:00', '14:00:00', 3, 3, 3);

-- Insert sample data into the 'preferences' table
INSERT INTO `preferences` (`id`, `preferred_day_of_week`, `preferred_end_time`, `preferred_start_time`,
                           `preferred_room_id`, `user_id`)
VALUES (1, 'Monday', '18:00:00', '16:00:00', 1, 1),
       (2, 'Tuesday', '20:00:00', '18:00:00', 2, 2);

-- Insert sample data into the 'user_course' table
INSERT INTO `user_course` (`user_id`, `course_id`)
VALUES (1, 1),
       (2, 2),
       (1, 3);
