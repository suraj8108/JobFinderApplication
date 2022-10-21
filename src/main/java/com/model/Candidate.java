package com.model;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

	@GeneratedValue
	@Id
	private int candidateId;
	
	private String candidateName;
	
	private int age;
	
	private String eduQualify;
	
	private int experience;
	
	private String location;
	
	@Column(unique = true)
	private String emailId;
	
	private String password;

	public Candidate(String candidateName, int age, String eduQualify, int experience, String location, String emailId,
			String password) {
		super();
		this.candidateName = candidateName;
		this.age = age;
		this.eduQualify = eduQualify;
		this.experience = experience;
		this.location = location;
		this.emailId = emailId;
		this.password = password;
	}
	
	
	
}
