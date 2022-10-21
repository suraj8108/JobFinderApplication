package com.model;

import javax.persistence.*;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name="CAND_TBL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Candidate {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int candidateId;
	
	@Column
	private String firstName;
	
	@Column
	private int age;
	
	@Column
	private String lastName;
	
	@Column
	private String educationalDetails;
	
	@Column
	private String experienceDetails;

	  @ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
	  @JoinTable(name="CANDIDATE_JOB_TABLE",
			joinColumns= {
					@JoinColumn(name="candidate_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name="job_id")
			})
	private Set<Job> jobSet = new HashSet<>();

}