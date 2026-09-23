package jsp.hospital_appointment_system.service;

import jsp.hospital_appointment_system.repository.SecurityRepository;
import jsp.hospital_appointment_system.validator_entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    SecurityRepository securityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = securityRepository.findByUserName(username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassWord())
                .roles(user.getRoles().stream()
                        .map(User.Role::name)
                        .toArray(String[]::new)).build();
    }
}
