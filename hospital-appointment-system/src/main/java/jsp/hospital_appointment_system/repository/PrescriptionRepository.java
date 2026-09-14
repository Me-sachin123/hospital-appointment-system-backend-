package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.MedicalRecord;
import jsp.hospital_appointment_system.modal.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {


    Prescription getByMedicalRecord(MedicalRecord record);

    List<Prescription> findBymedicalRecordPatientPatientId(Long id);
}
