package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.enums.InterviewStatus;
import com.enums.JobStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Interview {
  
  @Id
  @GeneratedValue
  @NotNull
  private int interviewId;
  
//  @JsonManagedReference
//  private Candidate candidate;
  private String candidate;
  
  @JsonManagedReference
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Employer employer;
  
  @JsonManagedReference
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Job job;
  
  private JobStatus jobStatus = JobStatus.INVALID;
  
  private InterviewStatus interviewStatus = InterviewStatus.INVALID;
  
  private int candidateRating;
  private int employerRating;
  private String candidateFeedback;
  private String employerFeedback;
}
