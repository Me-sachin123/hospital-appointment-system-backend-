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
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    private String doctorName;

    private String specialization;

    @JoinColumn(name="department_id")
    @ManyToOne
    private Department department;

    //add private List<DaysOfWeek> availabity
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> availability;

    //add appointment mapping
    @JsonIgnore
    @OneToMany(mappedBy = "doctor",cascade=CascadeType.ALL)
    private List<Appointment> appointments;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor" ,cascade=CascadeType.ALL)
    private List<MedicalRecord> records;
    //add medicalrecord mapping

    public enum DayOfWeek{
       SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

}
