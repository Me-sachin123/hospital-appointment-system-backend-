package jsp.hospital_appointment_system.service;


import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.exception.Business_exception.InsufficientInformationException;
import jsp.hospital_appointment_system.exception.Business_exception.InvalidAppointmentException;
import jsp.hospital_appointment_system.exception.Business_exception.NoDoctorAvailableException;
import jsp.hospital_appointment_system.exception.Business_exception.NoPatientAvailableException;
import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.modal.Patient;
import jsp.hospital_appointment_system.repository.AppointmentRepository;
import jsp.hospital_appointment_system.repository.DoctorRepository;
import jsp.hospital_appointment_system.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public ResponseDto<Appointment> createAppointment(Appointment appointment) {

        if (appointment.getDoctor().getDoctorId() == null || appointment.getPatient().getPatientId() == null)
            throw new InsufficientInformationException("to get appointment provide a patient and doctor data");

        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getDoctorId())
                .orElseThrow(() -> new NoDoctorAvailableException("no doctor record found"));

        Patient patient = patientRepository.findById(appointment.getPatient().getPatientId())
                .orElseThrow(() -> new NoPatientAvailableException("no patient record found"));

        List<Appointment> appointmentList = patient.getAppointments();
        for (Appointment appointment1 : appointmentList) {
            if (appointment1.getAppointmentDateTime().equals(appointment.getAppointmentDateTime()))
                throw new InvalidAppointmentException("already have appointment on this date" + appointment.getAppointmentDateTime());
        }

        List<Appointment> appointments = doctor.getAppointments();
        for (Appointment appointment1 : appointments) {
            if (appointment1.getAppointmentDateTime().equals(appointment.getAppointmentDateTime()))
                throw new InvalidAppointmentException("Can't book appointment already have appointment");
        }
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStatus(Appointment.Status.PENDING);

        return ResponseDto.<Appointment>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(appointmentRepository.save(appointment))
                .build();
    }

    public ResponseDto<List<Appointment>> getByAppointmentDate(LocalDate date) {

        List<Appointment> appointmentList = appointmentRepository.findAll();
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDateTime().toLocalDate().equals(date))
                appointments.add(appointment);
        }
        if (appointments.isEmpty())
            throw new NoPatientAvailableException("no patient appointment record found for date" + date);

        return ResponseDto.<List<Appointment>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(appointments)
                .build();
    }

    public ResponseDto<List<Appointment>> getAllAppointment() {

        List<Appointment> appointments = appointmentRepository.findAll();

        if (appointments.isEmpty())
            throw new InvalidAppointmentException("No appointment available");

        return ResponseDto.<List<Appointment>>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(appointments)
                .build();
    }

    public ResponseDto<Appointment> getById(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new InvalidAppointmentException("no appointment available by id :" + id));

        return ResponseDto.<Appointment>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(appointment)
                .build();
    }

    public ResponseDto<List<Appointment>> getByDoctor(long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorAvailableException("no doctor exists by id :" + id));

        return ResponseDto.<List<Appointment>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(doctor.getAppointments())
                .build();
    }

    public ResponseDto<List<Appointment>> getByPatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientAvailableException("no patient available by id :" + id));

        return ResponseDto.<List<Appointment>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(patient.getAppointments())
                .build();

    }

    public ResponseDto<List<Appointment>> getByAppointmentStatus(String status) {

        List<Appointment> appointments = appointmentRepository.getByStatus(Appointment.Status.valueOf(status.toUpperCase()));

        return ResponseDto.<List<Appointment>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(appointments)
                .build();
    }

    public ResponseDto<String> cancelAppointment(Long id) {

        Appointment appointment=appointmentRepository.findById(id)
                .orElseThrow(()->new InvalidAppointmentException("no appointment exists by id"+id));

        if(appointment.getStatus()== Appointment.Status.CANCELLED)
            throw new InvalidAppointmentException("Can't update appointment status already cancled");

        appointment.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(appointment);

        return ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data("Deleted")
                .build();
    }

    public ResponseDto<String> updateAppointmentStatus(Long id,String status) {

        Appointment appointment=appointmentRepository.findById(id)
                .orElseThrow(()->new InvalidAppointmentException("appointment unavailable"));

        appointment.setStatus(Appointment.Status.valueOf(status));
        appointmentRepository.save(appointment);

        return ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data("status updated")
                .build();


    }
}
