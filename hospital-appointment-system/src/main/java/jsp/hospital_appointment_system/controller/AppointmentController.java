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

    //handle a exception method mismatch exception
    @GetMapping("/appointmnet/{date}")
    public ResponseEntity<ResponseDto<List<Appointment>>> getPatientByAppointmentDate(@PathVariable LocalDate date)
    {
        return ResponseEntity.ok(appointmentService.getByAppointmentDate(date));
    }

}
