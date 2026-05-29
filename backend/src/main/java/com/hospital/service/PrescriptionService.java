package com.hospital.service;

import com.hospital.entity.Prescription;
import com.hospital.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    public Page<Prescription> getAllPrescriptions(int page, int size) {
        return prescriptionRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "prescriptionDate")));
    }

    public Page<Prescription> getPrescriptionsByPatient(Long patientId, int page, int size) {
        return prescriptionRepository.findByPatientId(patientId, PageRequest.of(page, size));
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
    }

    public Prescription createPrescription(Prescription prescription, Long patientId, Long doctorId, Long appointmentId) {
        prescription.setPatient(patientService.getPatientById(patientId));
        prescription.setDoctor(doctorService.getDoctorById(doctorId));
        if (appointmentId != null) {
            prescription.setAppointment(appointmentService.getAppointmentById(appointmentId));
        }
        if (prescription.getPrescriptionDate() == null) {
            prescription.setPrescriptionDate(LocalDate.now());
        }
        return prescriptionRepository.save(prescription);
    }

    public Prescription updatePrescription(Long id, Prescription details) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setDiagnosis(details.getDiagnosis());
        prescription.setMedications(details.getMedications());
        prescription.setInstructions(details.getInstructions());
        prescription.setValidUntil(details.getValidUntil());
        return prescriptionRepository.save(prescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
}
