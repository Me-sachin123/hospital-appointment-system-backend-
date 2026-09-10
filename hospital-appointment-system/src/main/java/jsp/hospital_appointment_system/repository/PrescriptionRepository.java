package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {


}
