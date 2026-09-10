package jsp.hospital_appointment_system.exception.Business_exception;

public class NoPatientAvailableException extends RuntimeException{
    public NoPatientAvailableException(String message)
    {
        super(message);
    }
}
