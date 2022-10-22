package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class JobSkillSet {

	@Id
	@GeneratedValue
	private int skillSetId;
	
	private String skillName;
	
	@ManyToOne
	@JsonBackReference
	private Job job;

	public JobSkillSet(String skillName) {
		super();
		this.skillName = skillName;
	}

	
	
	
}
