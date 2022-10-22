package com.controller;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.exception.CandidateNotFoundException;
import com.exception.ItemNotFoundException;
import com.exception.ValidationExceptioncheck;


@ControllerAdvice

public class ControllerAdvices {

	

    @ExceptionHandler({CandidateNotFoundException.class})
    public ResponseEntity<Object> handleItemNotFoundException(CandidateNotFoundException c,WebRequest req)
    {
        
        return  new ResponseEntity<>(c.toString(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationExceptioncheck.class)
    public ResponseEntity<Object> handleValdationException(ValidationExceptioncheck v,WebRequest req){
    	
    	 return  new ResponseEntity<>(v.toString(),HttpStatus.FORBIDDEN);
    }

	
	
}
