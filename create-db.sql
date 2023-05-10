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