package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.EmployerDAO;
import com.dto.NewEmployerDTO;
import com.model.Employer;

import io.swagger.annotations.ApiOperation;

@RestController
public class EmployerController {
	
	@Autowired
	EmployerDAO employerDAO;
	
	@ApiOperation(value = "add an employer", notes = "Adding a new employer", nickname = "add-employer")
	@PostMapping("/addEmployer")
	public ResponseEntity<String> addEmployer(@RequestBody NewEmployerDTO employerDTO) {
	    Employer employer = new Employer();
	    employer.setEmployerName(employerDTO.getEmployerName());
	    employer.setLocation(employerDTO.getLocation());
		employerDAO.save(employer);
		return new ResponseEntity<>("Employer saved successfully", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getAllEmployers")
	public ResponseEntity<List<Employer>> getAllEmployers() {
		return new ResponseEntity<>(employerDAO.findAll(), HttpStatus.OK);
	}
}
