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

}
