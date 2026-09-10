package jsp.hospital_appointment_system.controller;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.modal.Patient;
import jsp.hospital_appointment_system.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto<Patient>> savePatient(@RequestBody Patient patient){
       return ResponseEntity.ok(patientService.createPatient(patient));
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<List<Patient>>> getAllPatient()
    {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Patient>> getPatientById(@PathVariable Long id)
    {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/contact/{number}")
    public ResponseEntity<ResponseDto<Patient>> getPatientByContact(@PathVariable Long number)
    {
        return ResponseEntity.ok(patientService.getPatientByContact(number));
    }

   @GetMapping("appointment/{id}")
   public  ResponseEntity<ResponseDto<Patient>> getByAppointment(@PathVariable Long id)
   {
       return ResponseEntity.ok(patientService.getByAppointment(id));
   }

    //get by medical record

    @GetMapping("doctor/{id}")
    public ResponseEntity<ResponseDto<List<Patient>>> getByDoctor(@PathVariable Long id)
    {
        return ResponseEntity.ok(patientService.getByDoctor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Patient>> updatePatient(@PathVariable Long id,@RequestBody Map<String,Object> map){
        return ResponseEntity.ok(patientService.updatePatient(id,map));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deletePatient(@PathVariable Long id)
    {
        return ResponseEntity.ok(patientService.deletePatient(id));
    }
}
