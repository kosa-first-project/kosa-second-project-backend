package com.example.kosa_second_project_backend.controller;

import com.example.kosa_second_project_backend.entity.ScheduleEntity;
import com.example.kosa_second_project_backend.repository.schedule.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private final ScheduleRepository repository;

    public ScheduleController(ScheduleRepository repository) {
        this.repository = repository;
    }

    // Get all schedules
    @GetMapping("")
    public ResponseEntity<List<ScheduleEntity>> findAll() {
        List<ScheduleEntity> scheduleList = repository.findAll();

        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }
    @GetMapping("/{title}")
    public ResponseEntity<List<ScheduleEntity>> findByTitle(@PathVariable String title) {
        List<ScheduleEntity> scheduleList = repository.findByTitle(title);

        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    // Create a new schedule
    @PostMapping("/create")
    public ResponseEntity<ScheduleEntity> createSchedule(
            @RequestParam("user_id") String user_id, // Demo code for test
            @RequestParam("destination") String destination,
            @RequestParam("travel_start_date") String startDate,
            @RequestParam("travel_start_time") String startTime,
            @RequestParam("travel_end_date") String endDate,
            @RequestParam("travel_end_time") String endTime,
            @RequestParam(required = false) String comment,
            @RequestParam("title") String title) throws Exception {

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startDateTime = dateTimeFormat.parse(startDate + " " + startTime);
        Date endDateTime = dateTimeFormat.parse(endDate + " " + endTime);

//        Test
//        HttpSession session = request.getSession();
//        String user_id = (String)session.getAttribute("user_id");
//        if (user_id == null) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }

        ScheduleEntity schedule = new ScheduleEntity(user_id ,title, destination, startDateTime, endDateTime, comment);
        repository.save(schedule);

        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduleEntity> updateSchedule(
            @PathVariable int id,
            @RequestParam("destination") String destination,
            @RequestParam("travel_start_date") String startDate,
            @RequestParam("travel_start_time") String startTime,
            @RequestParam("travel_end_date") String endDate,
            @RequestParam("travel_end_time") String endTime,
            @RequestParam(required = false) String comment,
            @RequestParam("title") String title,
            HttpServletRequest request) throws Exception {

        Optional<ScheduleEntity> existingScheduleOpt = repository.findById(id);
        if (existingScheduleOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpSession session = request.getSession();
        String user_id = (String)session.getAttribute("user_id");
        if (user_id == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startDateTime = dateTimeFormat.parse(startDate + " " + startTime);
        Date endDateTime = dateTimeFormat.parse(endDate + " " + endTime);

        ScheduleEntity existingSchedule = existingScheduleOpt.get();
        existingSchedule.setTitle(title);
        existingSchedule.setDestination(destination);
        existingSchedule.setStartDateTime(startDateTime);
        existingSchedule.setEndDateTime(endDateTime);
        existingSchedule.setComment(comment);

        ScheduleEntity updatedSchedule = repository.save(existingSchedule);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    // Delete a schedule by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
