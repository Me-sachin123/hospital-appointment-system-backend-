package jsp.hospital_appointment_system.controller;

import jsp.hospital_appointment_system.dto.PrescriptionDto;
import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.Prescription;
import jsp.hospital_appointment_system.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//check all api
@RestController
@RequestMapping("prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/save/{id}")
    public ResponseEntity<ResponseDto<Prescription>> createPrescription(@PathVariable Long id,@RequestBody Prescription prescription)
    {
        return ResponseEntity.ok(prescriptionService.createPrescription(id,prescription));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<Prescription>>> getAllAppointment()
    {
        return ResponseEntity.ok(prescriptionService.getAllPrescription());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Prescription>> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(prescriptionService.getById(id));
    }

    @GetMapping("/record/{id}")
    public ResponseEntity<PrescriptionDto> getByMedicalRecord(@PathVariable Long id)
    {
        return ResponseEntity.ok(prescriptionService.getByMedicalRecord(id));
    }

    @GetMapping("patient/{id}")
    public ResponseEntity<List<Prescription>> getByPatient(@PathVariable Long id)
    {
        return ResponseEntity.ok(prescriptionService.getByPatient(id));
    }
}
