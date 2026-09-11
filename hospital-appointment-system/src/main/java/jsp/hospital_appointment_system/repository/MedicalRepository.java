package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRepository extends JpaRepository<MedicalRecord,Long> {

}
