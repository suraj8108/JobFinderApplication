package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Project;

public interface ProjectDao extends JpaRepository<Project, Integer> {

}
