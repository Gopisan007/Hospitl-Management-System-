package com.hospital.repository;

import com.hospital.entity.Appointment;
import com.hospital.entity.Appointment.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);
    Page<Appointment> findByStatus(Status status, Pageable pageable);
    Page<Appointment> findByAppointmentDate(LocalDate date, Pageable pageable);
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate date);

    long countByStatus(Status status);
    long countByAppointmentDate(LocalDate date);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.appointmentDate BETWEEN :start AND :end")
    long countByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT a.appointmentDate, COUNT(a) FROM Appointment a " +
           "WHERE a.appointmentDate BETWEEN :start AND :end " +
           "GROUP BY a.appointmentDate ORDER BY a.appointmentDate")
    List<Object[]> getAppointmentCountByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
