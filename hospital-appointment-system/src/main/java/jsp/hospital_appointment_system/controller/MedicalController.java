package jsp.hospital_appointment_system.controller;

import jsp.hospital_appointment_system.dto.RecordResponseDto;
import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.modal.MedicalRecord;
import jsp.hospital_appointment_system.service.MedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ClientInfoStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medical")
public class MedicalController {

    @Autowired
    MedicalService medicalService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto<RecordResponseDto>> createMedicalRecord(@RequestBody MedicalRecord medicalRecord)
    {
        return ResponseEntity.ok(medicalService.createMedicalRecord(medicalRecord));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RecordResponseDto>>> getAllRecord()
    {
        return ResponseEntity.ok(medicalService.getAllRecord());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RecordResponseDto>> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(medicalService.getById(id));
    }

    @GetMapping("patient/{id}")
    public ResponseEntity<List<RecordResponseDto>> getByPatient(@PathVariable Long id)
    {
        return ResponseEntity.ok(medicalService.getByPatient(id));
    }

    @GetMapping("doctor/{id}")
    public ResponseEntity<List<RecordResponseDto>> getByDoctor(@PathVariable Long id)
    {
        return ResponseEntity.ok(medicalService.getByDoctor(id));
    }

    @GetMapping("date/{date}")
    public ResponseEntity<List<RecordResponseDto>> getByVisitDate(@PathVariable LocalDate date)
    {
        return ResponseEntity.ok(medicalService.getByVisitDate(date));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RecordResponseDto> updateRecord(@PathVariable Long id, Map<String,Object> data)
    {
        return ResponseEntity.ok(medicalService.updateRecord(id,data));
    }

    @GetMapping("/appointment")
    public ResponseEntity<List<RecordResponseDto>> getByAppointment(@RequestParam long id)
    {
        return ResponseEntity.ok(medicalService.getByAppointment(id));
    }
}
