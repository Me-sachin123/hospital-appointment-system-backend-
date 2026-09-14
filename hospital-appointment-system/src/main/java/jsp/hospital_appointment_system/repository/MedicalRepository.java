package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.modal.MedicalRecord;
import jsp.hospital_appointment_system.modal.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MedicalRepository extends JpaRepository<MedicalRecord,Long> {

    List<MedicalRecord> getByPatient(Patient patient);

    List<MedicalRecord> getByDoctor(Doctor doctor);

    List<MedicalRecord> getByVisitDate(LocalDate date);


//    @Query("select m from MedicalRecord m where m.patient.appointmentId=:id")
//    MedicalRecord getByAppointment(long id);

    List<MedicalRecord> findByPatientAppointmentsAppointmentId(Long id);
}
