package jsp.hospital_appointment_system.exception.Business_exception;

public class EmptyDepartmentException extends RuntimeException{
    public EmptyDepartmentException(String message)
    {
        super(message);
    }
}
