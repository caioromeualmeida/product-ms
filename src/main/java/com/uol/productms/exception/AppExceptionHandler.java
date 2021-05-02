package com.uol.productms.exception;

import java.util.ArrayList;
import java.util.List;

import com.uol.productms.model.response.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    //handler padrão para formatar o corpo das exceptions mais comuns
    @ExceptionHandler(value = {Exception.class, NullPointerException.class, TransactionSystemException.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
        String descricaoErro = ex.getLocalizedMessage();

        if(descricaoErro == null){
            descricaoErro = ex.toString();
        }

        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), descricaoErro);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //handler que ajusta o corpo da mensagem, caso haja erro no corpo do json informado
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, org.springframework.http.HttpStatus status, WebRequest request) {
        String descricaoErro = "Request body is malformed.";
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), descricaoErro);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //handler que ajusta o corpo da mensagem, formatando os campos com erros de notação @valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, org.springframework.http.HttpStatus status, WebRequest request) {
        final List<String> mensagensErro = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            mensagensErro.add("Field " + error.getField() + ": " + error.getDefaultMessage());
        }

        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            mensagensErro.add("Field " + error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorResponse errors = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), mensagensErro.toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //handler exception 404
    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request){
        String descricaoErro = ex.getLocalizedMessage();

        if(descricaoErro == null){
            descricaoErro = ex.toString();
        }

        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), descricaoErro);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }    
}
