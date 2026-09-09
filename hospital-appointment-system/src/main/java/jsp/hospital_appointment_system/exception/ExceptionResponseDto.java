package jsp.hospital_appointment_system.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ExceptionResponseDto {

    private HttpStatus status;

    private String message;

}
