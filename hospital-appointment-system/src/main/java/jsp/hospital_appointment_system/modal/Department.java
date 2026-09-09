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
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DepartmentId;

    @Column(unique = true)
    private String departmentName;

    @JsonIgnore
    @OneToMany(mappedBy ="department",cascade=CascadeType.ALL)
    List<Doctor> doctors;

}
