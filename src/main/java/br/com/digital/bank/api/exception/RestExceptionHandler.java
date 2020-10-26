package br.com.digital.bank.api.exception;

import br.com.digital.bank.api.dto.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> HandlerDomain(DomainException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Exception exception = new Exception();
        exception.setStatus(status.value());
        exception.setTitulo(ex.getMessage());
        ResponseDto responseDto = new ResponseDto("Um erro aconteceu!",exception);
        return handleExceptionInternal(ex,responseDto,new HttpHeaders(),status,request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Exception exception = new Exception();
        exception.setStatus(status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        exception.setTitulo(errors.toString().replace("[","").replace("]",""));
        ResponseDto responseDto = new ResponseDto("Erro!",exception);
        return handleExceptionInternal(ex,responseDto,new HttpHeaders(),status,request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> HandlerResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Exception exception = new Exception();
        exception.setStatus(status.value());
        exception.setTitulo(ex.getMessage());
        ResponseDto responseDto = new ResponseDto("Erro!",exception);
        return handleExceptionInternal(ex,responseDto,new HttpHeaders(),status,request);
    }
}
