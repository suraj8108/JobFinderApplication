package com.model;

import java.util.Arrays;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Candidate {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int candidateId;
	//@NotNull
	private String candidateName;
	//@Min(value = 18)
	private  int age;
	//@Min(value=0)
	private int experience;
	private String location;
	private String educationQualification;
	
	@Column(unique = true)
	private String emailId;
	
	private String password;
	
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Project> projectList;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<CandidateSkill> canditationSkillSet;

	public Candidate(int candidateId, String candidateName, int age, int experience, String location,
			String educationQualification, String emailId, String password, List<Project> projectList,
			Set<CandidateSkill> canditationSkillSet) {
		super();
		this.candidateId = candidateId;
		this.candidateName = candidateName;
		this.age = age;
		this.experience = experience;
		this.location = location;
		this.educationQualification = educationQualification;
		this.emailId = emailId;
		this.password = password;
		this.projectList = projectList;
		this.canditationSkillSet = canditationSkillSet;
	}

	public Candidate(String candidateName, int age, int experience, String location, String educationQualification,
			String emailId, String password, List<Project> projectList, Set<CandidateSkill> canditationSkillSet) {
		super();
		this.candidateName = candidateName;
		this.age = age;
		this.experience = experience;
		this.location = location;
		this.educationQualification = educationQualification;
		this.emailId = emailId;
		this.password = password;
		this.projectList = projectList;
		this.canditationSkillSet = canditationSkillSet;
	}
	
}
