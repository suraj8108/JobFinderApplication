package com.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.model.Project;
import com.model.Skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {
    @NotNull
    private String candidateName;
    @Min(value = 20)
    private int age;
    
    private int experience;
    
    private String location;
    
    private String educationQualification;
    
    @NotNull
    private String emailId;
    
    @NotNull
    private String password;
    
    private List<Project> projectList;
    
    private Set<Skill> skillSet;
    
}
