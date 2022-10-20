package com.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor

public class CandidateSkill {
	@Id
	@GeneratedValue
	private int skillId;
	
	private String skillName;
	
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<Candidate>  candidateSet;
	
	


	


	
}
