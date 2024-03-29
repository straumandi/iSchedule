# iSchedule

iSchedule is a small full-stack scheduling application that allows users to manage their courses, rooms, and schedules.
It provides a user-friendly interface built with Thymeleaf & Bootstrap for creating and organizing course schedules,
making it easy to keep track of class timings, room assignments, and student enrollments.

## Planned Features

- **User Management:** Create user accounts with different roles (admin, assistant, student) to manage the application.
- **Course Management:** Add, update, and delete courses, including course name, start time, end time, and room
  assignment.
- **Room Management:** Manage rooms where courses are conducted, including room names and availability.
- **Enrollment Management:** Enroll students in courses and track their enrollments.
- **Schedule Display:** Display the weekly schedule with course details, room assignments, and timings.
- **Preferences:** Allow admins and assistants to set their preferred start time, end time, and room.

## Implemented Features

- **User Management:** Create user accounts with STUDENT role. (DB Admin can change role, user should not choose his
  role)
- **Course Management:** Add and delete courses, including course name, start time, end time, and room assignment.
- **Enrollment Management:** Enroll students in courses and track their enrollments.
- **Schedule Display:** Display the schedule with course details, room assignments, and timings.
- **Preferences:** Allow admins and assistants to set and change their preferences.
- **Admin Actions:** Allow admins to create and delete new courses.
- **Additional to required features:** 
  -    Login with Spring Security
  -    Registration with password encryption
  -    Modern UI

## Technologies Used

- Java 17
- Thymeleaf (with Bootstrap)
- Hibernate and Spring Boot (with MySQL)

## Installation

1. Clone the repository: `git clone https://github.com/straumandi/ischedule.git`
2. Create a local database named 'ischedule' with username 'root' and no password.
3. (Optional: For quick start) Import the ischedule.sql from the root of this repository into your local database.
4. Build the project using your preferred IDE. (project created with IntelliJ) 
5. Run the application and access the iSchedule UI.

## Usage

1. Launch the application and create a user account or login with existing credentials (password 12345 for all current
   Users).
2. Use the provided functionality to manage courses, rooms, and enrollments according to your requirements.
3. View and update the schedule to see course details and room assignments.
4. Set preferences if you have admin or assistant privileges to customize your scheduling preferences.

## Contributing

Contributions to iSchedule are welcome! If you have any ideas, feature requests, or bug reports, please open an issue on
the GitHub repository.  
Feel free to submit pull requests with improvements or fixes as well.

## License

This project is licensed under [The Unlicense](https://opensource.org/licenses/unlicense).
