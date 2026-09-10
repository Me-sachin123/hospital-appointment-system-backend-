package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.modal.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    //for check patient multiple appointment
    boolean existsByPatientAndAppointmentDateTime(Patient patient, LocalDateTime start, LocalDateTime end);

    //for check doctor multiple appointment
   // boolean existsByDoctorAndAppointmentDateAndTime(Doctor doctor, LocalDateTime start, LocalDateTime end);
}
