package com.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.exception.CandidateNotFoundException;
import com.exception.CandidateValidationExceptioncheck;
import com.exception.FormatException;
import com.exception.ProjectNotFoundException;
import com.exception.feedbackException;
import com.exception.skillNotFoundException;





@ControllerAdvice
public class ControllerAdvices extends ResponseEntityExceptionHandler {
    

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
   
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException v,WebRequest req){
        
        return  new ResponseEntity<>(v.toString(),HttpStatus.FORBIDDEN);
   }
    @ExceptionHandler(skillNotFoundException.class)
    public ResponseEntity<Object> handleSkillNotFoundException(skillNotFoundException v,WebRequest req){
        
        return  new ResponseEntity<>(v.toString(),HttpStatus.FORBIDDEN);
   }
    @ExceptionHandler(feedbackException.class)
    public ResponseEntity<Object> handlefeedbackException(feedbackException v,WebRequest req){
        
        return  new ResponseEntity<>(v.toString(),HttpStatus.FORBIDDEN);
   }
    
    
}
