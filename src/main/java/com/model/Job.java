package com.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job {
	
	@Id
	@GeneratedValue
	private int jobId;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<JobSkillSet> jobSkillSet;

	public Job(Status status, List<JobSkillSet> jobSkillSet) {
		super();
		this.status = status;
		this.jobSkillSet = jobSkillSet;
	}
	
	
}
