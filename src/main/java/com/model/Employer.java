package com.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//import org.hibernate.validator.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Embeddable
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "employerId")
public class Employer {
	
	@Id
	@GeneratedValue
//	@NotNull
	private int employerId;
	private String employerName;
	private String location;
	
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
//	@OrderColumn
//	@JsonIgnore
//	@JsonBackReference
//    @JsonManagedReference
	private List<Job> jobs;
}
