package jsp.hospital_appointment_system.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String patientName;
    private String medicine;
    private String dosage;
    private String instruction;

    @Column(unique = true)
    private Long contact;

    //add List<Appointment>mapping
    @JsonIgnore
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    List<Appointment> appointments;


    //add List<MedicalRecord>mapping
    @JsonIgnore
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    List<MedicalRecord> medicalRecords;
}
