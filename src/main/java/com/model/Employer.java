package com.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employer {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private int employerId;
	
	private String employerName;
	
	
	private String Location;
	
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.ALL},mappedBy = "employer")
	private List<Job> job;
	
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.ALL},mappedBy = "employer")
	private List<Interview> interview;
	
	
	
	
	
	
	
	
}
