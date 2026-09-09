package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
