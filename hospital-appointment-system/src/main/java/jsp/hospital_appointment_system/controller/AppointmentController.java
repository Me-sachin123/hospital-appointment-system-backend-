package jsp.hospital_appointment_system.controller;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.Patient;
import jsp.hospital_appointment_system.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto<Appointment>> createAppointment(@RequestBody Appointment appointment)
    {
        return ResponseEntity.ok(appointmentService.createAppointment(appointment));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<Appointment>>> getAllAppointment()
    {
        return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

    //handle an exception method mismatch exception
    @GetMapping("/appointmnet/{date}")
    public ResponseEntity<ResponseDto<List<Appointment>>> getPatientByAppointmentDate(@PathVariable LocalDate date)
    {
        return ResponseEntity.ok(appointmentService.getByAppointmentDate(date));
    }

    @GetMapping("by/{id}")
    public ResponseEntity<ResponseDto<Appointment>> getByAppointmentId(@PathVariable Long id)
    {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @GetMapping("doctor/{id}")
    public ResponseEntity<ResponseDto<List<Appointment>>> getByDoctor(@PathVariable Long id)
    {
        return ResponseEntity.ok(appointmentService.getByDoctor(id));
    }

    @GetMapping("patient/{id}")
    public ResponseEntity<ResponseDto<List<Appointment>>> getByPatient(@PathVariable Long id )
    {
        return ResponseEntity.ok(appointmentService.getByPatient(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseDto<List<Appointment>>> getByStatus(@PathVariable String status)
    {
        return ResponseEntity.ok(appointmentService.getByAppointmentStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> cancelAppointment(@PathVariable Long id)
    {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<ResponseDto<String>> updateAppointmentStatus(@PathVariable Long id,@PathVariable String status)
    {
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id,status));
    }
}
