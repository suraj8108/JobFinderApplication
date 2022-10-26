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
    
    private String emailId;
    
    private String password;
    
    private List<ProjectDTO> projectDTOList;
    
    private Set<SkillDTO> skillDTOSet;

    public ProfileDTO(@NotNull String candidateName, @Min(20) int age, int experience, String location,
            String educationQualification, String emailId, String password, List<ProjectDTO> projectDTOList,
            Set<SkillDTO> skillDTOSet) {
        super();
        this.candidateName = candidateName;
        this.age = age;
        this.experience = experience;
        this.location = location;
        this.educationQualification = educationQualification;
        this.emailId = emailId;
        this.password = password;
        this.projectDTOList = projectDTOList;
        this.skillDTOSet = skillDTOSet;
    }

    
    
    
}
