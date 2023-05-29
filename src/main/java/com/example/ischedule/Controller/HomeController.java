package com.example.ischedule.Controller;

import com.example.ischedule.Model.*;
import com.example.ischedule.Service.CourseService;
import com.example.ischedule.Service.PreferencesService;
import com.example.ischedule.Service.RoomService;
import com.example.ischedule.Service.UserService;
import com.example.ischedule.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final UserService userService;
    private final CourseService courseService;
    private final PreferencesService preferencesService;
    private final RoomService roomService;

    public HomeController(UserService userService, CourseService courseService, PreferencesService preferencesService, RoomService roomService) {
        this.userService = userService;
        this.courseService = courseService;
        this.preferencesService = preferencesService;
        this.roomService = roomService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        // Get currently logged-in user and add it to the Model
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();

        // Determine the user's role and set attributes in the model
        boolean isAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isAssistant = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
        boolean isStudent = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isAssistant", isAssistant);
        model.addAttribute("isStudent", isStudent);

        // Get the enrolled courses for the student and add them to the model
        if (isStudent) {
            User currentUserObject = userService.getUserByUsername(username);
            List<Course> enrolledCourses = courseService.getEnrolledCourses(currentUserObject.getId());
            model.addAttribute("enrolledCourses", enrolledCourses);
        } else {
            model.addAttribute("isStudent", false);
        }

        // Fetch preferences and rooms only for assistant and admin roles for setting preferences
        Preferences preferences = null;
        List<Room> rooms = null;
        if (isAdmin || isAssistant) {
            preferences = preferencesService.getPreferencesByUserId(currentUser.getUser().getId());
            rooms = roomService.getAllRooms();
            List<User> adminAndAssistantUsers = userService.getUsersByRoleIn(UserRole.ADMIN, UserRole.ASSISTANT);
            model.addAttribute("adminAndAssistantUsers", adminAndAssistantUsers);
        }
        model.addAttribute("preferences", preferences);
        model.addAttribute("rooms", rooms);

        // Get all courses for Students and Admins
        List<Course> allCourses = courseService.getAllCourses();
        model.addAttribute("allCourses", allCourses);
        return "home";
    }

    @Transactional
    @PostMapping("/deleteCourse")
    public String deleteCourse(@RequestParam("courseId") int courseId) {
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isPresent()) {
            Course courseToDelete = course.get();
            for (User user : courseToDelete.getEnrolledUsers()) {
                user.getEnrolledCourses().remove(courseToDelete);
            }
            courseToDelete.getEnrolledUsers().clear();
            courseService.deleteCourse(courseId);
        }
        return "redirect:/home";
    }

    @Transactional // Make sure every step of the whole transaction process is successful, otherwise db remains unchanged
    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute Course course,
                            @RequestParam("instructorId") int instructorId,
                            @RequestParam("dayOfWeek") DayOfWeek dayOfWeek,
                            @RequestParam("startTime") String startTimeStr,
                            @RequestParam("endTime") String endTimeStr,
                            @RequestParam("roomId") int roomId) {
        // Get current user to set admin_id for the new course
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User admin = userService.getUserByUsername(auth.getName());
        course.setAdmin(admin);
        // Get user_id from template to set Instructor of the new course
        User instructor = userService.getUserById(instructorId);
        course.setInstructor(instructor);

        CourseSchedule courseSchedule = new CourseSchedule();
        courseSchedule.setCourse(course);
        courseSchedule.setDayOfWeek(dayOfWeek);

        // Convert the string values to LocalTime to prevent parsing error
        LocalTime startTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime endTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ISO_LOCAL_TIME);
        courseSchedule.setStartTime(Time.valueOf(startTime));
        courseSchedule.setEndTime(Time.valueOf(endTime));

        Room room = roomService.getRoomById(roomId);
        courseSchedule.setRoom(room);
        course.setCourseSchedule(courseSchedule);

        courseService.saveCourse(course);
        return "redirect:/home";
    }

    @Transactional
    @PostMapping("/enroll")
    public String enrollCourse(@RequestParam("courseId") int courseId, Authentication authentication) {
        String username = authentication.getName();
        Optional<Course> course = courseService.getCourseById(courseId);
        User user = userService.getUserByUsername(username);
        courseService.enrollUserInCourse(user, course);
        return "redirect:/home";
    }

    @Transactional
    @PostMapping("/createPreferences")
    public String createPreferences(Model model) {
        // Logic to create an empty preferences object associated with the logged-in user
        Preferences preferences = new Preferences();
        // Set the logged-in user as the owner of the preferences object
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);
        preferences.setUser(user);
        // Save the preferences object to the database
        preferencesService.savePreferences(preferences);
        // Add the preferences object to the model
        model.addAttribute("preferences", preferences);
        return "redirect:/home";
    }

    @Transactional
    @PostMapping("/savePreferences")
    public String savePreferences(@RequestParam("startTime") String startTimeStr,
                                  @RequestParam("endTime") String endTimeStr,
                                  @RequestParam("room") int roomId,
                                  @RequestParam("dayOfWeek") DayOfWeek dayOfWeek,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Preferences preferences = preferencesService.getPreferencesByUserId(user.getId());
        Room preferredRoom = roomService.getRoomById(roomId);

        // Convert the string values to LocalTime to prevent parsing error
        LocalTime startTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime endTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ISO_LOCAL_TIME);

        // Update & save preferences
        preferences.setPreferredStartTime(Time.valueOf(startTime));
        preferences.setPreferredEndTime(Time.valueOf(endTime));
        preferences.setPreferredRoom(preferredRoom);
        preferences.setPreferredDayOfWeek(dayOfWeek);
        preferencesService.savePreferences(preferences);

        redirectAttributes.addFlashAttribute("successMessage", "Preferences saved successfully!");
        return "redirect:/home";
    }
}

