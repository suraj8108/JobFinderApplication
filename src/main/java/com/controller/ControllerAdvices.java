package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.exception.CandidateNotFoundException;
import com.exception.CandidateValidationExceptioncheck;
import com.exception.FormatException;





@ControllerAdvice
public class ControllerAdvices {
    

    @ExceptionHandler({CandidateNotFoundException.class})
    public ResponseEntity<Object> handleItemNotFoundException(CandidateNotFoundException c,WebRequest req)
    {
        
        return  new ResponseEntity<>(c.toString(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CandidateValidationExceptioncheck.class)
    public ResponseEntity<Object> handleValdationException(CandidateValidationExceptioncheck v,WebRequest req){
        
         return  new ResponseEntity<>(v.toString(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FormatException.class)
    public ResponseEntity<Object> handleFormatException(FormatException v,WebRequest req){
        
         return  new ResponseEntity<>(v.toString(),HttpStatus.FORBIDDEN);
    }
}
