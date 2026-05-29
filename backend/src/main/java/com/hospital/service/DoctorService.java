package com.hospital.service;

import com.hospital.entity.Doctor;
import com.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Page<Doctor> getAllDoctors(int page, int size) {
        return doctorRepository.findAll(PageRequest.of(page, size, Sort.by("firstName")));
    }

    public Page<Doctor> searchDoctors(String query, int page, int size) {
        if (query == null || query.isBlank()) {
            return getAllDoctors(page, size);
        }
        return doctorRepository.searchDoctors(query, PageRequest.of(page, size));
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = getDoctorById(id);
        doctor.setFirstName(doctorDetails.getFirstName());
        doctor.setLastName(doctorDetails.getLastName());
        doctor.setEmail(doctorDetails.getEmail());
        doctor.setPhone(doctorDetails.getPhone());
        doctor.setSpecialization(doctorDetails.getSpecialization());
        doctor.setQualification(doctorDetails.getQualification());
        doctor.setExperience(doctorDetails.getExperience());
        doctor.setLicenseNumber(doctorDetails.getLicenseNumber());
        doctor.setAddress(doctorDetails.getAddress());
        doctor.setAvailable(doctorDetails.isAvailable());
        doctor.setConsultationFee(doctorDetails.getConsultationFee());
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public long getTotalDoctors() { return doctorRepository.count(); }
    public long getAvailableDoctors() { return doctorRepository.countByAvailable(true); }
}
