package jsp.hospital_appointment_system.service;

import jsp.hospital_appointment_system.dto.RecordResponseDto;
import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.exception.Business_exception.*;
import jsp.hospital_appointment_system.modal.*;
import jsp.hospital_appointment_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MedicalService {

    @Autowired
    MedicalRepository medicalRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    public ResponseDto<RecordResponseDto> createMedicalRecord(MedicalRecord medicalRecord) {

        if (medicalRecord.getDoctor().getDoctorId() == null || medicalRecord.getPatient().getPatientId() == null)
            throw new InsufficientInformationException("information unsatisfied");

        Doctor doctor = doctorRepository.findById(medicalRecord.getDoctor().getDoctorId())
                .orElseThrow(() -> new NoDoctorAvailableException("No doctor record found"));

        Patient patient = patientRepository.findById(medicalRecord.getPatient().getPatientId())
                .orElseThrow(() -> new NoPatientAvailableException("No patient record found"));

//        Prescription prescription = prescriptionRepository.findById(medicalRecord.getPrescriptions().getPrescriptionId())
//                .orElseThrow(() -> new NoPrescriptionFoundException("no prescription record found"));

        if (medicalRecord.getVisitDate() == null)
            throw new InsufficientInformationException("provide visit date");

        boolean validAppointment = false;

        List<Appointment> appointmentList = patient.getAppointments();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDateTime().toLocalDate().equals(medicalRecord.getVisitDate())) {
                if (appointment.getStatus().equals(Appointment.Status.COMPLETED)) {
                    validAppointment = true;
                    medicalRecord.setVisitDate(appointment.getAppointmentDateTime().toLocalDate());
                    break;
                } else {
                    throw new InvalidAppointmentException("Appointment is not completed for this date");
                }

            }
        }

        if (!validAppointment) {
            throw new InvalidAppointmentException(
                    "No appointment found for this patient on this date");
        }

        medicalRecord.setDoctor(doctor);
        medicalRecord.setPatient(patient);
        // medicalRecord.setPrescriptions(prescription);
        MedicalRecord record = medicalRepository.save(medicalRecord);

        RecordResponseDto response = RecordResponseDto.builder()
                .recordId(record.getRecordId())
                .diagnosis(record.getDiagnosis())
                .treatment(record.getTreatment())
                .visitDate(record.getVisitDate())
                .doctorName(doctor.getDoctorName())
                .patientName(record.getPatient().getPatientName())
                .specialization(record.getDoctor().getSpecialization())
                .build();


        return ResponseDto.<RecordResponseDto>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(response)
                .build();


    }

    public ResponseDto<List<RecordResponseDto>> getAllRecord() {

        List<MedicalRecord> medicalRecords = medicalRepository.findAll();
        if (medicalRecords.isEmpty())
            throw new InvalidAppointmentException("no appointment unavailable");

        List<RecordResponseDto> record = new ArrayList<>();

        for (MedicalRecord medicalRecord : medicalRecords) {
            RecordResponseDto recordResponseDto = RecordResponseDto.builder()
                    .recordId(medicalRecord.getRecordId())
                    .treatment(medicalRecord.getTreatment())
                    .diagnosis(medicalRecord.getDiagnosis())
                    .visitDate(medicalRecord.getVisitDate())
                    .doctorName(medicalRecord.getDoctor().getDoctorName())
                    .patientName(medicalRecord.getPatient().getPatientName())
                    .build();
            record.add(recordResponseDto);
        }

        return ResponseDto.<List<RecordResponseDto>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(record)
                .build();
    }

    public ResponseDto<RecordResponseDto> getById(Long id) {

        MedicalRecord record = medicalRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordException("medicalRecord unavailable"));

        RecordResponseDto responseDto = RecordResponseDto.builder()
                .recordId(record.getRecordId())
                .treatment(record.getTreatment())
                .diagnosis(record.getDiagnosis())
                .visitDate(record.getVisitDate())
                .specialization(record.getDoctor().getSpecialization())
                .doctorName(record.getDoctor().getDoctorName())
                .patientName(record.getPatient().getPatientName())
                .build();

        return ResponseDto.<RecordResponseDto>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(responseDto)
                .build();


    }

    public List<RecordResponseDto> getByPatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoPatientAvailableException("no patient record exist"));

        List<MedicalRecord> record = medicalRepository.getByPatient(patient);

        List<RecordResponseDto> responseDtos = new ArrayList<>();


        for (MedicalRecord Record : record) {
            responseDtos.add(RecordResponseDto.builder()
                    .patientName(Record.getPatient().getPatientName())
                    .diagnosis(Record.getDiagnosis())
                    .treatment(Record.getTreatment())
                    .doctorName(Record.getDoctor().getDoctorName())
                    .recordId(Record.getRecordId())
                    .specialization(Record.getDoctor().getSpecialization())
                    .visitDate(Record.getVisitDate())
                    .build());
        }
        return responseDtos;
    }

    public List<RecordResponseDto> getByDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorAvailableException("no doctor record exist"));

        List<MedicalRecord> record = medicalRepository.getByDoctor(doctor);

        List<RecordResponseDto> responseDtos = new ArrayList<>();


        for (MedicalRecord Record : record) {
            responseDtos.add(RecordResponseDto.builder()
                    .patientName(Record.getPatient().getPatientName())
                    .diagnosis(Record.getDiagnosis())
                    .treatment(Record.getTreatment())
                    .doctorName(Record.getDoctor().getDoctorName())
                    .recordId(Record.getRecordId())
                    .specialization(Record.getDoctor().getSpecialization())
                    .visitDate(Record.getVisitDate())
                    .build());
        }
        return responseDtos;
    }

    public List<RecordResponseDto> getByVisitDate(LocalDate date) {

        List<MedicalRecord> records = medicalRepository.getByVisitDate(date);


        List<RecordResponseDto> responseDtos = new ArrayList<>();


        for (MedicalRecord Record : records) {
            responseDtos.add(RecordResponseDto.builder()
                    .patientName(Record.getPatient().getPatientName())
                    .diagnosis(Record.getDiagnosis())
                    .treatment(Record.getTreatment())
                    .doctorName(Record.getDoctor().getDoctorName())
                    .recordId(Record.getRecordId())
                    .specialization(Record.getDoctor().getSpecialization())
                    .visitDate(Record.getVisitDate())
                    .build());
        }
        return responseDtos;


    }

    public RecordResponseDto updateRecord(Long id, Map<String, Object> data) {

        MedicalRecord record = medicalRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordException("no medical record exits in db"));

        for (Map.Entry<String, Object> map : data.entrySet()) {
            String key = map.getKey();
            switch (key) {
                case "diagnosis":
                    record.setDiagnosis((String) map.getValue());
                    break;

                case "treatment":
                    record.setTreatment((String) map.getValue());
                    break;

            }
        }

        MedicalRecord medicalRecord = medicalRepository.save(record);
        return RecordResponseDto.builder()
                .recordId(medicalRecord.getRecordId())
                .treatment(medicalRecord.getTreatment())
                .diagnosis(medicalRecord.getDiagnosis())
                .visitDate(medicalRecord.getVisitDate())
                .doctorName(medicalRecord.getDoctor().getDoctorName())
                .specialization(medicalRecord.getDoctor().getSpecialization())
                .patientName(medicalRecord.getPatient().getPatientName())
                .build();

    }

    public List<RecordResponseDto> getByAppointment(long id) {

        Appointment appointment = appointmentRepository.findById(id).
                orElseThrow(() -> new MedicalRecordException("medical record unavailable"));

        List<MedicalRecord> medicalRecordList = medicalRepository.findByPatientAppointmentsAppointmentId(id);

        List<RecordResponseDto> responseDtos = new ArrayList<>();
        for (MedicalRecord record : medicalRecordList) {
            responseDtos.add(RecordResponseDto.builder()
                    .recordId(record.getRecordId())
                    .diagnosis(record.getDiagnosis())
                    .treatment(record.getTreatment())
                    .visitDate(record.getVisitDate())
                    .patientName(record.getPatient().getPatientName())
                    .doctorName(record.getDoctor().getDoctorName())
                    .specialization(record.getDoctor().getSpecialization())
                    .build());
        }

        return responseDtos;
    }
}
