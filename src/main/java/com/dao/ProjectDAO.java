package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Project;

public interface ProjectDAO extends JpaRepository<Project, Integer> {

 

}
