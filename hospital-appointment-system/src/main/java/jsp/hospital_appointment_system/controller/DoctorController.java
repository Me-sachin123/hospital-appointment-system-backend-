package jsp.hospital_appointment_system.controller;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
