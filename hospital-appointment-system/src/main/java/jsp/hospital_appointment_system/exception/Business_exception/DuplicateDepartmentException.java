package jsp.hospital_appointment_system.exception.Business_exception;

public class DuplicateDepartmentException extends RuntimeException {

    public DuplicateDepartmentException(String message)
    {
        super(message);
    }
}
