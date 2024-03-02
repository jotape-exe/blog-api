package com.jorgedev.creditapp.exceptions;

import com.jorgedev.creditapp.service.exceptions.ListNotFoundException;
import com.jorgedev.creditapp.service.exceptions.ObjectNotFoundException;
import com.jorgedev.creditapp.service.exceptions.StringSizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleObjectNotFoundException(
            ObjectNotFoundException objectNotFoundException,
            WebRequest request) {
        return buildErrorResponse(
                objectNotFoundException,
                "Nenhum registro encontrado",
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(ListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleListNotFoundException(
            ListNotFoundException listNotFoundException,
            WebRequest request) {
        return buildErrorResponse(
                listNotFoundException,
                "Sem dados",
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(StringSizeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleStringSizeException(
            StringSizeException stringSizeException,
            WebRequest request) {
        return buildErrorResponse(
                stringSizeException,
                "Tamanho do conteudo inválido",
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
            Exception exception,
            WebRequest request) {
        return buildErrorResponse(
                exception,
                "Erro, contate o suporte",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }
    

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }


}
