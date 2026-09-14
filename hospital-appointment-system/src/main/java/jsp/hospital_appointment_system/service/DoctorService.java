package jsp.hospital_appointment_system.service;

import jakarta.servlet.UnavailableException;
import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.exception.Business_exception.*;
import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.Department;
import jsp.hospital_appointment_system.modal.Doctor;
import jsp.hospital_appointment_system.modal.Patient;
import jsp.hospital_appointment_system.repository.AppointmentRepository;
import jsp.hospital_appointment_system.repository.DepartmentRepository;
import jsp.hospital_appointment_system.repository.DoctorRepository;
import jsp.hospital_appointment_system.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    public ResponseDto<Doctor> saveDoctor(Doctor doctor) {

        if (doctor.getDoctorName() == null)
            throw new InsufficientDoctorInformationException("Doctor name must needed");

        if (doctor.getDepartment() == null || doctor.getDepartment().getDepartmentId() == null)
            throw new InsufficientDoctorInformationException("must provide department info to create doctor");

        Department department = departmentRepository.findById(doctor.getDepartment().getDepartmentId())
                .orElseThrow(() -> new EmptyDepartmentException("Assigned Department is not exists in DB"));

        doctor.setDepartment(department);
        return ResponseDto.<Doctor>builder()
                .status(HttpStatus.ACCEPTED)
                .message("success")
                .data(doctorRepository.save(doctor))
                .build();
    }

    public ResponseDto<List<Doctor>> getAllDoctors() {

        List<Doctor> doctors = doctorRepository.findAll();

        if (doctors.isEmpty())
            throw new NoDoctorAvailableException("no doctors record found");

        return ResponseDto.<List<Doctor>>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(doctors)
                .build();
    }

    public ResponseDto<Doctor> getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorAvailableException("No doctor record exists by Id :" + id));

        return ResponseDto.<Doctor>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(doctor)
                .build();
    }

    public ResponseDto<List<Doctor>> getDoctorByDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EmptyDepartmentException("No department exists with id :" + id));

        return ResponseDto.<List<Doctor>>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(doctorRepository.getByDepartment(department))
                .build();

    }

    public ResponseDto<List<Doctor>> getDoctorBySpecialization(String specialization) {

        List<Doctor> doctors = doctorRepository.getBySpecialization(specialization);
        if (doctors.isEmpty())
            throw new NoDoctorAvailableException("No doctor record exists in DB for Specialization :" + specialization);

        return ResponseDto.<List<Doctor>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(doctors)
                .build();

    }

    public ResponseDto<List<Doctor>> getByAvailableDays(List<Doctor.DayOfWeek> days) {

        List<Doctor> doctors = doctorRepository.getByAvailabilityIn(days);
        if (doctors.isEmpty())
            throw new NoDoctorAvailableException("No doctor available on these :" + days);

        return ResponseDto.<List<Doctor>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(doctors)
                .build();
    }


    public ResponseDto<String> updateAvailability(Doctor doctor) {

        if (doctor.getDoctorId() == null)
            throw new InsufficientDoctorInformationException("Must provide doctor id to update record");

        if (doctor.getAvailability().isEmpty())
            throw new InsufficientDoctorInformationException("availability not be null");

        Doctor doc = doctorRepository.findById(doctor.getDoctorId())
                .orElseThrow(() -> new NoDoctorAvailableException("no doctor record exists in Db by id:" + doctor.getDoctorId()));
        doc.setAvailability(doctor.getAvailability());

        doctorRepository.save(doctor);

        return ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data("availability updated")
                .build();

    }

    public ResponseDto<Doctor> getDoctorByAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new InvalidAppointmentException("appointment unavailable"));

        Long doctorId = appointment.getDoctor().getDoctorId();

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NoDoctorAvailableException("doctor unavailable"));

        return ResponseDto.<Doctor>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(doctor)
                .build();
    }

    public ResponseDto<List<Doctor>> getByPatient(Long id) {

        Patient patient=patientRepository.findById(id)
                .orElseThrow(()->new NoPatientAvailableException("patient unavailable"));

        return ResponseDto.<List<Doctor>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(appointmentRepository.findByPatient(id))
                .build();


    }
}
