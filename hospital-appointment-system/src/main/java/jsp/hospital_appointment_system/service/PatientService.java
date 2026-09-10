package jsp.hospital_appointment_system.service;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.exception.Business_exception.*;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public ResponseDto<Patient> createPatient(Patient patient) {

        if (String.valueOf(patient.getContact()).length() != 10)
            throw new InsufficientInformationException("provide valid contact number");

        return ResponseDto.<Patient>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(patientRepository.save(patient))
                .build();
    }

    public ResponseDto<List<Patient>> getAllPatients() {

        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty())
            throw new NoPatientAvailableException("no doctors record found");

        return ResponseDto.<List<Patient>>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(patients)
                .build();
    }

    public ResponseDto<Patient> getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientAvailableException("No patient record exists by Id :" + id));

        return ResponseDto.<Patient>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(patient)
                .build();
    }


    public ResponseDto<Patient> getPatientByContact(Long number) {

        Patient patient = patientRepository.getByContact(number)
                .orElseThrow(() -> new NoPatientAvailableException("No patient record exists by contact :" + number));

        return ResponseDto.<Patient>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(patient)
                .build();
    }

    public ResponseDto<Patient> updatePatient(Long id, Map<String, Object> map) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientAvailableException("No patient present is exists with id :" + id));

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            switch (key) {
                case "patientName":
                    patient.setPatientName((String) map.get(key));
                    break;

                case "medicine":
                    patient.setMedicine((String) map.get(key));
                    break;

                case "dosage":
                    patient.setDosage((String) map.get(key));
                    break;

                case "instruction":
                    patient.setInstruction((String) map.get(key));
                    break;

                case "contact":
                    if (String.valueOf((Long) map.get(key)).length() != 10)
                        throw new InsufficientInformationException("provide valid contact number");
                    else
                        patient.setContact((Long) map.get(key));
            }

        }

        return ResponseDto.<Patient>builder()
                .status(HttpStatus.OK)
                .message("updated")
                .data(patientRepository.save(patient))
                .build();

    }

    public ResponseDto<String> deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientAvailableException("No patient present is exists with id :" + id));

        if (!patient.getAppointments().isEmpty() && !patient.getMedicalRecords().isEmpty())
            throw new PatientDeletionException("Can't delete patient associated other information");

        patientRepository.delete(patient);

        return ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data("Record deleted successfully")
                .build();
    }


    public ResponseDto<Patient> getByAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new InvalidAppointmentException("no appointment found by id"));

        Patient patient = appointment.getPatient();
        return ResponseDto.<Patient>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(patient)
                .build();
    }

    public ResponseDto<List<Patient>> getByDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorAvailableException("No exists by this name"));

        List<Patient> patients = new ArrayList<>();

        List<Appointment> appointments = doctor.getAppointments();

        for (Appointment appointment : appointments)
            patients.add(appointment.getPatient());

        if (patients.isEmpty())
            throw new NoPatientAvailableException("No patient available");

        return ResponseDto.<List<Patient>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(patients)
                .build();

    }
}
