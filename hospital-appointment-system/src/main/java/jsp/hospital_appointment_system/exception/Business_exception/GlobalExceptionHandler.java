package jsp.hospital_appointment_system.exception.Business_exception;

import jsp.hospital_appointment_system.exception.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //duplicate department provided by client
    @ExceptionHandler(DuplicateDepartmentException.class)
    public ExceptionResponseDto duplicateDepartmentException(DuplicateDepartmentException exception)
    {
        return ExceptionResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("duplicate department Record already exists")
                .build();
    }

   //if no department present
    @ExceptionHandler(EmptyDepartmentException.class)
    public ExceptionResponseDto emptyDepartmentException(EmptyDepartmentException exception)
    {
       return ExceptionResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
    }

    //for id not given
    @ExceptionHandler(InsufficientInformationException.class)
    public ExceptionResponseDto emptyDepartmentException(InsufficientInformationException exception)
    {
        return ExceptionResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
    }

    //for department deletion with having doctors
    @ExceptionHandler(DepartmentDeletionException.class)
    public ExceptionResponseDto emptyDepartmentException(DepartmentDeletionException exception)
    {
        return ExceptionResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
    }

    //----------------------------------------------Doctor Exception handlers----------------------------------------------------//

    @ExceptionHandler(InsufficientDoctorInformationException.class)
    public ExceptionResponseDto insufficientDoctorInformationException(InsufficientDoctorInformationException exception)
    {
        return ExceptionResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
    }

}
