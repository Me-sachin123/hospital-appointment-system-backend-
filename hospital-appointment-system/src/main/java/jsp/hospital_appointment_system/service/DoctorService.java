package jsp.hospital_appointment_system.service;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.exception.Business_exception.EmptyDepartmentException;
import jsp.hospital_appointment_system.exception.Business_exception.InsufficientDoctorInformationException;
import jsp.hospital_appointment_system.modal.Department;
import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.repository.DepartmentRepository;
import jsp.hospital_appointment_system.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public ResponseDto<Doctor> saveDoctor(Doctor doctor) {

        if(doctor.getDepartment()==null)
            throw new InsufficientDoctorInformationException("must provide department info to create doctor");

        Department department=departmentRepository.findById(doctor.getDepartment().getDepartmentId())
                .orElseThrow(()->new EmptyDepartmentException("Assigned Department is not exists in DB"));

        doctor.setDepartment(department);
        return ResponseDto.<Doctor>builder()
                .status(HttpStatus.ACCEPTED)
                .message("success")
                .data(doctorRepository.save(doctor))
                .build();
    }
}
