package jsp.hospital_appointment_system.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.DecimalMax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtCustomFilter extends OncePerRequestFilter {
    @Autowired
    JwtParser jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header=request.getHeader("authorization");

        if(header!=null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

        Claims claims=jwtParser.parseSignedClaims(token).getPayload();

            List<String> roles = claims.get("roles", List.class);

            List<GrantedAuthority> authorities = new ArrayList<>();

            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            Authentication authentication=new UsernamePasswordAuthenticationToken(claims.getSubject(),null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
        filterChain.doFilter(request,response);
    }
}
