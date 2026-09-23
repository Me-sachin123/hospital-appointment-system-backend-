package jsp.hospital_appointment_system.configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jsp.hospital_appointment_system.exception.Generic_Exception.CustomAccessDeniedHandler;
import jsp.hospital_appointment_system.exception.Generic_Exception.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
   private JwtCustomFilter jwtCustomFilter;

    @Autowired
   private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(login -> login.disable());
        http.httpBasic(basic -> basic.disable());
        http.authorizeHttpRequests((auth -> auth.requestMatchers("/security/**").permitAll()));
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/patient/save").hasAnyRole("USER"));
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/doctor/**", "/patient/**").hasAnyRole("DOCTOR", "ADMIN"));

        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.addFilterBefore(jwtCustomFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider getProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }




    @Bean
    public AuthenticationManager getManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }


}
