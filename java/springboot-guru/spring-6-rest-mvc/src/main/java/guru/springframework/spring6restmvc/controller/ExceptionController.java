package guru.springframework.spring6restmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBindErrors(MethodArgumentNotValidException e) {
        List<?> errorList = e.getFieldErrors().stream().map(fieldError -> {
            assert fieldError.getDefaultMessage() != null;
            return Map.of(fieldError.getField(), fieldError.getDefaultMessage());
        }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleJPAViolation(TransactionSystemException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
