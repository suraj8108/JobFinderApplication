package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectId;
	private String projectName;
	private String projectDescription;
	
	@JsonBackReference
	@ManyToOne
	private Candidate candidate;

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [projectName=").append(projectName).append(", projectDescription=")
				.append(projectDescription).append(", candidate=").append(candidate).append("]");
		return builder.toString();
	}
	
	

	
	
	
	
}
