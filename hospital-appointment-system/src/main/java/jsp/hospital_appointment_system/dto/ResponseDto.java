package jsp.hospital_appointment_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
public class ResponseDto <T>{

    private HttpStatus status;

    private String message;

    private T data;
}
