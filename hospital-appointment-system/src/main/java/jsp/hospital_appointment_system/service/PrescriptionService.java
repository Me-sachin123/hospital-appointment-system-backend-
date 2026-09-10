package jsp.hospital_appointment_system.service;

import jsp.hospital_appointment_system.dto.ResponseDto;
import jsp.hospital_appointment_system.exception.Business_exception.NoPrescriptionFoundException;
import jsp.hospital_appointment_system.modal.Appointment;
import jsp.hospital_appointment_system.modal.MedicalRecord;
import jsp.hospital_appointment_system.modal.Prescription;
import jsp.hospital_appointment_system.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public ResponseDto<Prescription> createPrescription(Prescription prescription) {

        //validate if medical record exists or not
        MedicalRecord medicalRecord;

        return ResponseDto.<Prescription>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(prescriptionRepository.save(prescription))
                .build();

    }

    public ResponseDto<List<Prescription>> getAllAppointment() {

        List<Prescription> prescriptions=prescriptionRepository.findAll();
        if(prescriptions.isEmpty())
            throw new NoPrescriptionFoundException("No prescription record found");
        return ResponseDto.<List<Prescription>>builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(prescriptions)
                .build();
    }

    public ResponseDto<Prescription> getById(Long id) {

        Prescription prescription=prescriptionRepository.findById(id)
                .orElseThrow(()->new NoPrescriptionFoundException("No prescription record found by id:"+id));
        return ResponseDto.<Prescription>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(prescription)
                .build();
    }
}
