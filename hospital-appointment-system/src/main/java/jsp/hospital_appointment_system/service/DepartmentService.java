package jsp.hospital_appointment_system.service;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.exception.Business_exception.DepartmentDeletionException;
import jsp.hospital_appointment_system.exception.Business_exception.DuplicateDepartmentException;
import jsp.hospital_appointment_system.exception.Business_exception.EmptyDepartmentException;
import jsp.hospital_appointment_system.exception.Business_exception.InsufficientInformationException;
import jsp.hospital_appointment_system.modal.Department;
import jsp.hospital_appointment_system.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;


    public ResponseDto<Department> saveDepartment(Department department)  {

        Department department1=departmentRepository.findBydepartmentName(department.getDepartmentName());
       if(department1!=null)
       {
           throw new DuplicateDepartmentException("Department already exists");
       }
       else {
           return ResponseDto.<Department>builder()
                   .status(HttpStatus.OK)
                   .message("success")
                   .data(departmentRepository.save(department))
                   .build();
       }


    }


    public ResponseDto<List<Department>> getAllDepartemnt() {

        List<Department> departments=departmentRepository.findAll();
        if(departments.isEmpty())
            throw new EmptyDepartmentException("No Department Record Found");

        return ResponseDto.<List<Department>>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(departmentRepository.findAll())
                .build();
    }

    public ResponseDto<Department> getByDepartmentId(Long id) {

        Optional<Department> department=departmentRepository.findById(id);
        if(department.isPresent())
        {
            return ResponseDto.<Department>builder()
                    .status(HttpStatus.OK)
                    .message("Success")
                    .data(department.get())
                    .build();
        }
        throw new EmptyDepartmentException("No department exists by this Id");
    }


    public ResponseDto<String> updateDepartmentByName(Department department) {

        if(department.getDepartmentId()==null)
            throw new InsufficientInformationException("Id must needed to update a record");

        Department dept=departmentRepository.findById(department.getDepartmentId())
                .orElseThrow(()-> new EmptyDepartmentException("No department exists with Id:"+department.getDepartmentId()));
          dept.setDepartmentName(department.getDepartmentName());
          Department department1=departmentRepository.save(dept);

        return ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data("Department Name Updated : "+department1.getDepartmentName())
                .build();
    }

    public ResponseDto<Department> getByDepartmentName(String name) {
       Department department= departmentRepository.findBydepartmentName(name);
       if(department!=null)
       {
           return ResponseDto.<Department>builder()
                   .status(HttpStatus.OK)
                   .message("Success")
                   .data(department)
                   .build();
       }
       throw new EmptyDepartmentException("Department not Exists in DB");
    }

    public ResponseDto<String> deleteDepartemnt(Department department) {
        if(department.getDepartmentId()==null)
            throw new InsufficientInformationException("Id must needed to update a record");

        Department dept=departmentRepository.findById(department.getDepartmentId())
                .orElseThrow(()-> new EmptyDepartmentException("No department exists with Id:"+department.getDepartmentId()));

        if(!dept.getDoctors().isEmpty())
            throw new DepartmentDeletionException("Department cannot be deleted because doctors are associated with it");

        departmentRepository.delete(department);

        return ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data("Department deleted Successfully")
                .build();
    }
}
