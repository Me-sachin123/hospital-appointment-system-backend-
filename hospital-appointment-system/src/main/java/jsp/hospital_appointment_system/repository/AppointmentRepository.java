package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.modal.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    //for check patient multiple appointment
  //  boolean existsByPatientAndAppointmentDateTime(Patient patient, LocalDateTime start, LocalDateTime end);

    List<Appointment> getByStatus(Appointment.Status status);


    @Query("select a.doctor from Appointment a where a.patient.patientId=:p_id")
    List<Doctor> findByPatient(@Param("p_id") Long id);

    //for check doctor multiple appointment
   // boolean existsByDoctorAndAppointmentDateAndTime(Doctor doctor, LocalDateTime start, LocalDateTime end);
}
