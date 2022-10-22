package com.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.enums.JobPostStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
//@NamedQuery(name = "job.findByLocation", query = "select j from job j where j.created_by_employer_id in (select e.employer_id from employer e where e.location = :location)")
@NamedQuery(name = "job.findByIndustry", query = "select j from Job j where j.industry = :industry")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "jobId")
public class Job {
	
	@Id
	@GeneratedValue
	@NotNull
	private int jobId;
	
	@NotNull
	private String jobDescription;
	private String industry;
	private JobPostStatus jobPostStatus;
	
	// Candidate[]
	private String candidateList;
	

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
	private Employer createdBy;
    
    @JsonBackReference
    @OneToMany
    private List<Interview> interviews;
    
    public Job(String desc, String industry) {
      super();
      this.jobDescription = desc;
      this.industry = industry;
      this.jobPostStatus = JobPostStatus.OPEN;
    }
	
    
	
	
}
