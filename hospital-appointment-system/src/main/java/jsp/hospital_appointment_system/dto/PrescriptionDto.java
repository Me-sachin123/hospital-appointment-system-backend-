package jsp.hospital_appointment_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PrescriptionDto {

    private Long prescriptionId;

    private String medicine;
    private String dosage;
    private String instruction;

    private RecordResponseDto record;
}
