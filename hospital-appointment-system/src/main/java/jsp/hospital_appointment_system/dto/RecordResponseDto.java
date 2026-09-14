package jsp.hospital_appointment_system.dto;

import jsp.hospital_appointment_system.modal.Prescription;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RecordResponseDto {

    private Long recordId;

    private String diagnosis;
    private String treatment;

    private LocalDate visitDate;

    private String doctorName;

    private String specialization;

    private String patientName;



}
