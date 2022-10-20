package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	private int projectId;
	private String projectName;
	private String projectDescription;
	
	@JsonBackReference
	@ManyToOne
	private Candidate candidate;

	public Project(String projectName, String projectDescription) {
		super();
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		
	}

	public Project(int projectId, String projectName, String projectDescription) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [projectName=").append(projectName).append(", projectDescription=")
				.append(projectDescription).append(", candidate=").append(candidate).append("]");
		return builder.toString();
	}
	
	

	
	
	
	
}
