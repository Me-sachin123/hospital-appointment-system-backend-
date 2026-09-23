package jsp.hospital_appointment_system.repository;

import jsp.hospital_appointment_system.validator_entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);
}
