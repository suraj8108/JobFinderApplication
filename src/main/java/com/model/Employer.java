package com.model;

import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="EMP_TBL")
@NoArgsConstructor
public class Employer {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private int employerId;
	
	private String employerName;
	
	
	private String location;
	
	@JsonManagedReference(value="employer_job")
	@OneToMany(cascade = {CascadeType.ALL},mappedBy = "createdBy")
	private List<Job> jobList;
	
	@JsonManagedReference(value="employer_interview")
	@OneToMany(cascade = {CascadeType.ALL},mappedBy = "employer")
	private List<Interview> interviewList;
	
	
	
	
	
	
	

}
