package jsp.hospital_appointment_system.validator_entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "provide username")
    private String userName;

    @NotNull(message = "provide password")
    private String passWord;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles;


    public enum Role
    {
        USER,DOCTOR,ADMIN
    }

}
