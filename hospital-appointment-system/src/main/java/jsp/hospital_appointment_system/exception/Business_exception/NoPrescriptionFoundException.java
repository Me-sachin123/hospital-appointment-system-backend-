package jsp.hospital_appointment_system.exception.Business_exception;

public class NoPrescriptionFoundException extends RuntimeException{
    public NoPrescriptionFoundException(String message)
    {
        super(message);
    }
}
