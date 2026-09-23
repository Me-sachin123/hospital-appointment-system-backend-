package jsp.hospital_appointment_system.service;

import io.jsonwebtoken.Jwts;
import jsp.hospital_appointment_system.repository.SecurityRepository;
import jsp.hospital_appointment_system.validator_entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;


@Service
public class SecurityService {

    @Autowired
    SecurityRepository securityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private SecretKey secretKey;

    public void registerUser(User user) {

        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        securityRepository.save(user);

    }

    public String login(User user) {

        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassWord()));

        Collection<? extends GrantedAuthority> roles =
                authentication.getAuthorities();
        

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("roles", roles.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey)
                .compact();
    }
}
