package com.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity
@Getter
@Setter
@NoArgsConstructor
public class Interview {

	@Id
	private int interviewId;
	@Enumerated(EnumType.STRING)
	private InterviewStatus interviewStatus = InterviewStatus.REJECTED;
	
	@Range(min = 0,max = 10)
	private int candidateRating;
	@Range(min = 0,max = 10)
	private int employerRating;
	
	private String candidateFeedback;
	
	private String employerFeedback;
	
	
	
	@JsonBackReference
	@ManyToOne
	private Candidate candidate;
	
	@JsonBackReference
	@ManyToOne
	private Employer employer;

	@JsonBackReference
	@ManyToOne
	private Job job;

	
	
	
	
}
