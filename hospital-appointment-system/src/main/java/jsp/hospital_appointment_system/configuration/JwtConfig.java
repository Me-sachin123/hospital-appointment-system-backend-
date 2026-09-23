package jsp.hospital_appointment_system.configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    @Bean
    public JwtParser getParser(SecretKey key) {
        return Jwts.parser().verifyWith(key).build();
    }

    @Bean
    public SecretKey getSecretKey() {
        String key = "ygewygd3t87t333gyg3bhsh388d8ew8bw88bd239j9j9iwjqj92nqec9cnddn9nsxb";
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    @Bean
    public PasswordEncoder getPassWordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
