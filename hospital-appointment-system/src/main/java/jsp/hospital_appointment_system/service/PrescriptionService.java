package jsp.hospital_appointment_system.service;

import jsp.hospital_appointment_system.dto.PrescriptionDto;
import jsp.hospital_appointment_system.dto.RecordResponseDto;
import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.exception.Business_exception.InsufficientInformationException;
import jsp.hospital_appointment_system.exception.Business_exception.MedicalRecordException;
import jsp.hospital_appointment_system.exception.Business_exception.NoPrescriptionFoundException;
import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.MedicalRecord;
import jsp.hospital_appointment_system.modal.Patient;
import jsp.hospital_appointment_system.modal.Prescription;
import jsp.hospital_appointment_system.repository.MedicalRepository;
import jsp.hospital_appointment_system.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicalRepository medicalRepository;


    public ResponseDto<Prescription> createPrescription(Long id,Prescription prescription) {

        MedicalRecord medicalRecord=medicalRepository.findById(id)
                .orElseThrow(()->new MedicalRecordException("medical record unavailable"));

        prescription.setMedicalRecord(medicalRecord);
        return ResponseDto.<Prescription>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(prescriptionRepository.save(prescription))
                .build();

    }

    public ResponseDto<List<Prescription>> getAllPrescription() {

        List<Prescription> prescriptions = prescriptionRepository.findAll();
        if (prescriptions.isEmpty())
            throw new NoPrescriptionFoundException("No prescription record found");
        return ResponseDto.<List<Prescription>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(prescriptions)
                .build();
    }

    public ResponseDto<Prescription> getById(Long id) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new NoPrescriptionFoundException("No prescription record found by id:" + id));
        return ResponseDto.<Prescription>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(prescription)
                .build();
    }

    public PrescriptionDto getByMedicalRecord(Long id) {

        MedicalRecord record = medicalRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordException("medical record unavailable"));

        Prescription prescription = prescriptionRepository.getByMedicalRecord(record);

        return PrescriptionDto.builder()
                .medicine(prescription.getMedicine())
                .dosage(prescription.getDosage())
                .instruction(prescription.getInstruction())
                .prescriptionId(prescription.getPrescriptionId())
                .record(RecordResponseDto.builder().recordId(prescription.getMedicalRecord().getRecordId())
                        .diagnosis(prescription.getMedicalRecord().getDiagnosis()).
                        treatment(prescription.getMedicalRecord().getTreatment()).
                        visitDate(prescription.getMedicalRecord().getVisitDate()).
                        doctorName(prescription.getMedicalRecord().getDoctor().getDoctorName()).
                        specialization(prescription.getMedicalRecord().getDoctor().getSpecialization()).
                        patientName(prescription.getMedicalRecord().getPatient().getPatientName())
                        .build()).build();
    }

    public List<Prescription> getByPatient(Long id) {

        List<Prescription> prescriptions=prescriptionRepository.findBymedicalRecordPatientPatientId(id);

        return prescriptions;
    }
}
