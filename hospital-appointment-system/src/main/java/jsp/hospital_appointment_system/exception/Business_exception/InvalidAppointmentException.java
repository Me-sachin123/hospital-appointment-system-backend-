package jsp.hospital_appointment_system.exception.Business_exception;

public class InvalidAppointmentException extends RuntimeException{
    public InvalidAppointmentException(String message)
    {
        super(message);
    }
}
