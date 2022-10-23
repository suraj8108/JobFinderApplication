package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.NoSuchElementException;

import com.dao.ProjectDAO;
import com.model.Project;
@Service
public class ProjectService {
	@Autowired
	ProjectDAO projectDao;

    public void remove(Project project) throws IllegalArgumentException,NoSuchElementException {
       Project p = projectDao.findById(project.getProjectId()).get();
       projectDao.delete(project);
        
    }

}
