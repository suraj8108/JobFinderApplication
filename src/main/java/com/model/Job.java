package com.model;

import java.util.*;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@Entity
@Table(name="JOB_TBL")
@Getter
@Setter
@NoArgsConstructor
public class Job {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int jobId;
	
	@Column
	private String status;
	
	@Column
	private String jobDescription;

	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      },
		      mappedBy = "jobSet")
	@JsonIgnore
	private Set<Candidate>  candidateSet = new HashSet<>();
	
}
