package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

//import org.hibernate.validator.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@NamedQuery(name = "job.findByLocation", query = "select j from job j where j.created_by_employer_id in (select e.employer_id from employer e where e.location = :location)")
@NamedQuery(name = "job.findByIndustry", query = "select j from Job j where j.industry = :industry")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "jobId")
public class Job {
	
	@Id
	@GeneratedValue
//	@NotNull
	private int jobId;
	
//	@NotNull
	private String jobDescription;
	private String industry;
	private String jobStatus;
	
	// Candidate[]
	private String[] candidateList;
	
	

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JsonBackReference
//    @JsonManagedReference
	private Employer createdBy;
	
    
	
	
}
