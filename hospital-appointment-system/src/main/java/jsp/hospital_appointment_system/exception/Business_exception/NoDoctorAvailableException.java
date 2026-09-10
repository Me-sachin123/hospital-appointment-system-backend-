package jsp.hospital_appointment_system.exception.Business_exception;

public class NoDoctorAvailableException extends RuntimeException{

 public NoDoctorAvailableException(String message)
 {
     super(message);
 }
}
