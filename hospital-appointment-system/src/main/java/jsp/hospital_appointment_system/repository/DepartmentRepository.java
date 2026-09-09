package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.modal.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    //get Department by name to check duplicate entry
    Department findBydepartmentName(String departmentName);
}
