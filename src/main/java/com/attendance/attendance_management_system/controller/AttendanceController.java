package com.attendance.attendance_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @GetMapping("/status")
    public String status() {
        return "Attendance Service Running";
    }

    @PostMapping("/checkin")
    public String checkin() {
        return "Check-in Successful";
    }
}