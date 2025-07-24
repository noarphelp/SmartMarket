package com.example.demo.exceptions;

import com.example.demo.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManejoDeExcepciones {

    @ExceptionHandler(RecursoNoEncontrado.class)
    public ResponseEntity<ErrorDTO> manejoRecursoNoEncontrado(RecursoNoEncontrado ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(DatosInvalidos.class)
    public ResponseEntity<ErrorDTO> manejoDatosInvalidos(DatosInvalidos ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
