package com.hospital.controller;

import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // Doctor stats
        stats.put("totalDoctors", doctorService.getTotalDoctors());
        stats.put("availableDoctors", doctorService.getAvailableDoctors());

        // Patient stats
        stats.put("totalPatients", patientService.getTotalPatients());

        // Appointment stats
        Map<String, Long> appointmentStats = appointmentService.getDashboardStats();
        stats.putAll(appointmentStats);

        return ResponseEntity.ok(stats);
    }
}
