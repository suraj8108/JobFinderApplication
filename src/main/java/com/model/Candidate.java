package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Candidate {
	@Id
	private int candidateId;
	private String firstName;
	private String lastName;
	private String educationalDetails;
	private String experienceDetails;

}
