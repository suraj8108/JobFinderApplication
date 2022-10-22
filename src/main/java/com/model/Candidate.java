package com.model;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter

@NoArgsConstructor
@Table(name="CAND_TBL")
public class Candidate {
	
	
	
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int candidateId;
	@NotNull
	private String candidateName;
	@Min(value = 20)
	private  int age;
	//@Min
	private int experience;
	private String location;
	private String educationQualification;
	
	
	
	//relations
	
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "candidate")
	private List<Project> projectList;
	
	
	
	  @ManyToMany(fetch = FetchType.LAZY,

		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })

	  @JoinTable(name="CANDIDATE_SKILL_TABLE",

			joinColumns= {
					@JoinColumn(name="candidate_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name="skill_id")
			})
	
	private Set<Skill> skillSet = new HashSet<>();
	  
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
	  
	  
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "candidate")
	private List<Interview> interview;
	  
	
	

	  
	  
	  
	
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  


	public Candidate(String candidateName, int age, int experience, String location, String educationQualification,
			List<Project> projectList, Set<Skill> skillSet) {
		super();
		this.candidateName = candidateName;
		this.age = age;
		this.experience = experience;
		this.location = location;
		this.educationQualification = educationQualification;
		this.projectList = projectList;
		this.skillSet = skillSet;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Candidate [candidateName=").append(candidateName).append(", age=").append(age)
				.append(", experience=").append(experience).append(", location=").append(location)
				.append(", educationQualification=").append(educationQualification).append(", skillSet=")
				.append(skillSet).append("]");
		return builder.toString();
	}




	
	
	
	


	



	
}


