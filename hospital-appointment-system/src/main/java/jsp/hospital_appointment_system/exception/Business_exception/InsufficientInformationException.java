package jsp.hospital_appointment_system.exception.Business_exception;

public class InsufficientInformationException extends RuntimeException{
    public InsufficientInformationException(String message)
    {
        super(message);
    }
}
