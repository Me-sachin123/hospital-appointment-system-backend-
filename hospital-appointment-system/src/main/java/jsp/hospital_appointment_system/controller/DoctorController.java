package jsp.hospital_appointment_system.controller;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto<Doctor>> saveDoctor(@RequestBody Doctor doctor)
    {
        return ResponseEntity.ok(doctorService.saveDoctor(doctor));
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<List<Doctor>>> getAllDoctors()
    {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Doctor>> getDoctorById(@PathVariable Long id)
    {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping("dept/{id}")
    public ResponseEntity<ResponseDto<List<Doctor>>> getDoctorByDepartment(@PathVariable Long id)
    {
        return ResponseEntity.ok(doctorService.getDoctorByDepartment(id));
    }

    @GetMapping("by/{specialization}")
    public ResponseEntity<ResponseDto<List<Doctor>>> getDoctorByDepartment(@PathVariable String specialization)
    {
        return ResponseEntity.ok(doctorService.getDoctorBySpecialization(specialization));
    }

    //handle exception for value that not enum
    @GetMapping("/avialday")
    public ResponseEntity<ResponseDto<List<Doctor>>> getByAvailableDays(@RequestBody List<Doctor.DayOfWeek> days)
    {
        return ResponseEntity.ok(doctorService.getByAvailableDays(days));
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<String>> updateAvailability(@RequestBody Doctor doctor)
    {
        return ResponseEntity.ok(doctorService.updateAvailability(doctor));
    }

    @GetMapping("appointment/{id}")
    public ResponseEntity<ResponseDto<Doctor>> getDoctorByAppointment(@PathVariable Long id)
    {
        return ResponseEntity.ok(doctorService.getDoctorByAppointment(id));
    }



    @GetMapping("patient")
    public ResponseEntity<ResponseDto<List<Doctor>>> getByPatient(@RequestParam Long id)
    {
        return ResponseEntity.ok(doctorService.getByPatient(id));
    }
    //impl getByPat

}
