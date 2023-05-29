-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 29. Mai 2023 um 13:38
-- Server-Version: 10.4.28-MariaDB
-- PHP-Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `ischedule`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `instructor_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `courses`
--

INSERT INTO `courses` (`id`, `description`, `name`, `admin_id`, `instructor_id`) VALUES
(1, 'Introduction to Programming', 'Programming 101', 3, 1),
(2, 'Web Development Basics', 'Web Dev 101', 3, 2),
(3, 'Database Management', 'DBMS', 3, 1),
(5, 'java baby', 'Datastructures & Algorithms in Java', 3, 5);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `course_schedule`
--

CREATE TABLE `course_schedule` (
  `id` int(11) NOT NULL,
  `day_of_week` varchar(255) NOT NULL,
  `end_time` time NOT NULL,
  `start_time` time NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `schedule_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `course_schedule`
--

INSERT INTO `course_schedule` (`id`, `day_of_week`, `end_time`, `start_time`, `course_id`, `room_id`, `schedule_id`) VALUES
(1, 'MONDAY', '12:00:00', '10:00:00', 1, 1, 1),
(2, 'WEDNESDAY', '14:00:00', '12:00:00', 2, 2, 2),
(3, 'FRIDAY', '16:00:00', '14:00:00', 3, 3, 3),
(4, 'MONDAY', '15:30:00', '13:30:00', 5, 3, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `preferences`
--

CREATE TABLE `preferences` (
  `id` int(11) NOT NULL,
  `preferred_day_of_week` varchar(255) DEFAULT NULL,
  `preferred_end_time` time DEFAULT NULL,
  `preferred_start_time` time DEFAULT NULL,
  `preferred_room_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `preferences`
--

INSERT INTO `preferences` (`id`, `preferred_day_of_week`, `preferred_end_time`, `preferred_start_time`, `preferred_room_id`, `user_id`) VALUES
(1, 'MONDAY', '18:00:00', '16:00:00', 2, 3),
(2, 'THURSDAY', '16:00:00', '14:00:00', 1, 2),
(3, 'FRIDAY', '18:00:00', '16:00:00', 2, 5);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rooms`
--

CREATE TABLE `rooms` (
  `id` int(11) NOT NULL,
  `capacity` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `rooms`
--

INSERT INTO `rooms` (`id`, `capacity`, `name`) VALUES
(1, 30, 'Room A'),
(2, 25, 'Room B'),
(3, 40, 'Room C');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schedules`
--

CREATE TABLE `schedules` (
  `id` int(11) NOT NULL,
  `week_number` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `schedules`
--

INSERT INTO `schedules` (`id`, `week_number`, `user_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `role`, `username`) VALUES
(1, 'john@example.com', '$2y$12$qZBG.TiVcPJrWW7wZL2zOu988sfqASQ2BAfk2mEkb1FkwnzJSzm2W', 'STUDENT', 'john'),
(2, 'emma@example.com', '$2y$12$qZBG.TiVcPJrWW7wZL2zOu988sfqASQ2BAfk2mEkb1FkwnzJSzm2W', 'ASSISTANT', 'emma'),
(3, 'admin@example.com', '$2y$12$qZBG.TiVcPJrWW7wZL2zOu988sfqASQ2BAfk2mEkb1FkwnzJSzm2W', 'ADMIN', 'admin'),
(4, 'peter@mail.com', '$2a$10$0uve.87p35T1wmemeBiCre0I.OtZG1nAhiGWD0K3z9sjXV06/D3Ga', 'STUDENT', 'peter'),
(5, 'max@muster.at', '$2a$10$eX7XQgPigHXudUZ8X48bCuSBq2LZbTaIyGX2ZodKB4GhCrItjxc8C', 'ASSISTANT', 'max');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_course`
--

CREATE TABLE `user_course` (
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `user_course`
--

INSERT INTO `user_course` (`user_id`, `course_id`) VALUES
(1, 1),
(1, 3),
(2, 2),
(4, 2);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp8k8u77nf521mtkpaumuy4nho` (`admin_id`),
  ADD KEY `FKcyfum8goa6q5u13uog0563gyp` (`instructor_id`);

--
-- Indizes für die Tabelle `course_schedule`
--
ALTER TABLE `course_schedule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKahfhilt2apyqcx619gvvc1pnw` (`course_id`),
  ADD KEY `FKk9cmt5jjq4bhcn19c26uwlty8` (`room_id`),
  ADD KEY `FKl9ufcldole32nbwqt9ono8ml7` (`schedule_id`);

--
-- Indizes für die Tabelle `preferences`
--
ALTER TABLE `preferences`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjosif7ab7dfsp13qkm0vs1w6o` (`preferred_room_id`),
  ADD KEY `FKme1hmam0h8w07410h7qkjna5m` (`user_id`);

--
-- Indizes für die Tabelle `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `schedules`
--
ALTER TABLE `schedules`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKd4y4xekwahv9boo6lc8gfl3jv` (`user_id`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`);

--
-- Indizes für die Tabelle `user_course`
--
ALTER TABLE `user_course`
  ADD PRIMARY KEY (`user_id`,`course_id`),
  ADD KEY `FKf4qni5wnlq0x70fm39w9tv7x4` (`course_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT für Tabelle `course_schedule`
--
ALTER TABLE `course_schedule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `preferences`
--
ALTER TABLE `preferences`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `schedules`
--
ALTER TABLE `schedules`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `FKcyfum8goa6q5u13uog0563gyp` FOREIGN KEY (`instructor_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKp8k8u77nf521mtkpaumuy4nho` FOREIGN KEY (`admin_id`) REFERENCES `users` (`id`);

--
-- Constraints der Tabelle `course_schedule`
--
ALTER TABLE `course_schedule`
  ADD CONSTRAINT `FKahfhilt2apyqcx619gvvc1pnw` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  ADD CONSTRAINT `FKk9cmt5jjq4bhcn19c26uwlty8` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  ADD CONSTRAINT `FKl9ufcldole32nbwqt9ono8ml7` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`);

--
-- Constraints der Tabelle `preferences`
--
ALTER TABLE `preferences`
  ADD CONSTRAINT `FKjosif7ab7dfsp13qkm0vs1w6o` FOREIGN KEY (`preferred_room_id`) REFERENCES `rooms` (`id`),
  ADD CONSTRAINT `FKme1hmam0h8w07410h7qkjna5m` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints der Tabelle `schedules`
--
ALTER TABLE `schedules`
  ADD CONSTRAINT `FKd4y4xekwahv9boo6lc8gfl3jv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints der Tabelle `user_course`
--
ALTER TABLE `user_course`
  ADD CONSTRAINT `FKf4qni5wnlq0x70fm39w9tv7x4` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  ADD CONSTRAINT `FKoc4yl478i6o8hf240vumhjgrb` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
