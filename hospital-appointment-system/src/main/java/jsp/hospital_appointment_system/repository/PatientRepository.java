package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

   Optional<Patient> getByContact(Long number);


}
