package jsp.hospital_appointment_system.controller;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.modal.Department;
import jsp.hospital_appointment_system.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto<Department>> saveDepartment(@RequestBody Department department)
    {
        return ResponseEntity.ok(departmentService.saveDepartment(department));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<Department>>> getAllDepartment()
    {
        return ResponseEntity.ok(departmentService.getAllDepartemnt());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Department>> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(departmentService.getByDepartmentId(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDto<String>> updateDepartment(@RequestBody Department department)
    {
        return ResponseEntity.ok(departmentService.updateDepartmentByName(department));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseDto<Department>> getByDepartmentName(@PathVariable String name)
    {
        return ResponseEntity.ok(departmentService.getByDepartmentName(name));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<String>> deleteDepartment(@RequestBody Department department)
    {
        return ResponseEntity.ok(departmentService.deleteDepartemnt(department));
    }
}
