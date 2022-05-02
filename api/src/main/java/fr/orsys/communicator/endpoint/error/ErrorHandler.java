package fr.orsys.communicator.endpoint.error;

import fr.orsys.communicator.security.exception.AuthenticationException;
import fr.orsys.communicator.security.exception.NotAuthorizedException;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@ResponseBody
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationErrorMap handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        ValidationErrorMap errors = new ValidationErrorMap();
        for (ObjectError error : exception.getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }
        for (FieldError error : exception.getFieldErrors()) {
            String fieldName = error.getField();
            if (fieldName.startsWith("data.")) {
                fieldName = fieldName.substring(5);
            }
            errors.put(fieldName, error.getDefaultMessage());
        }
        return errors;
    }
}
