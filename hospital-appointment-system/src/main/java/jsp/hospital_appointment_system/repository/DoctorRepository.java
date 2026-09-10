package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.Department;
import jsp.hospital_appointment_system.modal.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    //for fetching doctors record by department
    List<Doctor> getByDepartment(Department department);

    //for fetching doctors by specialization
    List<Doctor> getBySpecialization(String specialization);

    //for fetching doctor by availability
    List<Doctor> getByAvailabilityIn(List<Doctor.DayOfWeek> days);
}
