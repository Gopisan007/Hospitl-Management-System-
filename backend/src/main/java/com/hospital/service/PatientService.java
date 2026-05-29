package com.hospital.service;

import com.hospital.entity.Patient;
import com.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Page<Patient> getAllPatients(int page, int size) {
        return patientRepository.findAll(PageRequest.of(page, size, Sort.by("firstName")));
    }

    public Page<Patient> searchPatients(String query, int page, int size) {
        if (query == null || query.isBlank()) {
            return getAllPatients(page, size);
        }
        return patientRepository.searchPatients(query, PageRequest.of(page, size));
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient details) {
        Patient patient = getPatientById(id);
        patient.setFirstName(details.getFirstName());
        patient.setLastName(details.getLastName());
        patient.setEmail(details.getEmail());
        patient.setPhone(details.getPhone());
        patient.setDateOfBirth(details.getDateOfBirth());
        patient.setGender(details.getGender());
        patient.setBloodGroup(details.getBloodGroup());
        patient.setAddress(details.getAddress());
        patient.setEmergencyContact(details.getEmergencyContact());
        patient.setMedicalHistory(details.getMedicalHistory());
        patient.setAllergies(details.getAllergies());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public long getTotalPatients() { return patientRepository.count(); }
}
