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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    public HomeController(UserService userService, CourseService courseService, PreferencesService preferencesService, RoomService roomService, CourseController courseController) {
        this.userService = userService;
        this.courseService = courseService;
        this.preferencesService = preferencesService;
        this.roomService = roomService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();

        // Determine the user's role and set corresponding attributes in the model
        boolean isAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isAssistant = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
        boolean isStudent = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isAssistant", isAssistant);
        model.addAttribute("isStudent", isStudent);

        if (isStudent) {
            // Get the enrolled courses for the student and add them to the model
            User currentUserObject = userService.getUserByUsername(username);
            List<Course> enrolledCourses = courseService.getEnrolledCourses(currentUserObject.getId());
            model.addAttribute("enrolledCourses", enrolledCourses);
        } else {
            model.addAttribute("isStudent", false);
        }

        // Fetch preferences only for assistant and admin roles
        Preferences preferences = null;
        List<Room> rooms = null;
        if (isAdmin || isAssistant) {
            preferences = preferencesService.getPreferencesByUserId(currentUser.getUser().getId());
            rooms = roomService.getAllRooms();
        }
        model.addAttribute("preferences", preferences);
        model.addAttribute("rooms", rooms);

        // TODO: Add other necessary data to the model
        List<Course> allCourses = courseService.getAllCourses();
        model.addAttribute("allCourses", allCourses);

        return "home";
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
                                  Authentication authentication) {
        // Get the authenticated user's username
        String username = authentication.getName();

        // Get the user by username
        User user = userService.getUserByUsername(username);

        // Get the preferences for the user
        Preferences preferences = preferencesService.getPreferencesByUserId(user.getId());

        // Get the preferred room by ID
        Room preferredRoom = roomService.getRoomById(roomId);

        // Convert the string values to LocalTime
        LocalTime startTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime endTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ISO_LOCAL_TIME);

        // Update the preferences
        preferences.setPreferredStartTime(Time.valueOf(startTime));
        preferences.setPreferredEndTime(Time.valueOf(endTime));
        preferences.setPreferredRoom(preferredRoom);
        preferences.setPreferredDayOfWeek(dayOfWeek);

        // Save the updated preferences
        preferencesService.savePreferences(preferences);

        return "redirect:/home";
    }

}

