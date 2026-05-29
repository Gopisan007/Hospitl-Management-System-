package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.entity.Appointment.Status;
import com.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    public Page<Appointment> getAllAppointments(int page, int size) {
        return appointmentRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "appointmentDate", "appointmentTime")));
    }

    public Page<Appointment> getAppointmentsByDate(LocalDate date, int page, int size) {
        return appointmentRepository.findByAppointmentDate(date, PageRequest.of(page, size));
    }

    public Page<Appointment> getAppointmentsByStatus(Status status, int page, int size) {
        return appointmentRepository.findByStatus(status, PageRequest.of(page, size));
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    public Appointment createAppointment(Appointment appointment, Long patientId, Long doctorId) {
        appointment.setPatient(patientService.getPatientById(patientId));
        appointment.setDoctor(doctorService.getDoctorById(doctorId));
        return appointmentRepository.save(appointment);
    }

    public Appointment updateStatus(Long id, Status status) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public Map<String, Long> getDashboardStats() {
        LocalDate today = LocalDate.now();
        return Map.of(
                "totalAppointments", appointmentRepository.count(),
                "todayAppointments", appointmentRepository.countByAppointmentDate(today),
                "scheduled", appointmentRepository.countByStatus(Status.SCHEDULED),
                "completed", appointmentRepository.countByStatus(Status.COMPLETED),
                "cancelled", appointmentRepository.countByStatus(Status.CANCELLED)
        );
    }
}
