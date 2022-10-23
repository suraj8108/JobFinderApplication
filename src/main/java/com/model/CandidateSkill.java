package com.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
	@ManyToOne(cascade = CascadeType.ALL)
	private Candidate candidate;
	
	


	public CandidateSkill(String skillName) {
		super();
		this.skillName = skillName;
		
	}


	public CandidateSkill(int skillId, String skillName) {
		super();
		this.skillId = skillId;
		this.skillName = skillName;
		
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CandidateSkill [skillName=").append(skillName).append(", candidate=").append(candidate)
				.append("]");
		return builder.toString();
	}

	
	
}
