package com.example.Batch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.Batch.model.Employee;
import com.example.Batch.model.StudentRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService {

    private List<StudentRepo> studentRepos = new ArrayList<>();
    private boolean isDataFetched = false;  // Flag to track if data is already fetched

    /**
     * Fetches the student repositories from an external API and populates the list.
     * 
     * @return List of StudentRepo objects fetched from the API.
     */
    public List<StudentRepo> fetchStudentRepos() {
        log.info("Fetching student data from external service.");

        RestTemplate restTemplate = new RestTemplate();
        StudentRepo[] repos = restTemplate.getForObject("http://localhost:8081/all", StudentRepo[].class);

        // Check if response is valid and data is not already fetched
        if (repos != null && !isDataFetched) {
            for (StudentRepo student : repos) {
            	log.info(""+student);
                studentRepos.add(student);
            }
            isDataFetched = true;  // Mark that data has been fetched
        } else if (repos == null) {
        } else {
        }

        return studentRepos;
    }

    /**
     * Retrieves a single StudentRepo object. If the list is empty, it triggers the fetch.
     * 
     * @return StudentRepo object if available, otherwise null.
     */
    public StudentRepo getStudent(long id,String name) {
    	System.out.println(id+" "+name);
        log.info("Attempting to retrieve a student. Current list size: {}", studentRepos.size());

        // Fetch student data if the list is empty and data has not been fetched yet
        if (studentRepos.isEmpty() && !isDataFetched) {
            log.info("Student list is empty, fetching new data.");
            fetchStudentRepos();
        }

        // Check again after fetching and return if available
        if (!studentRepos.isEmpty()) {
            log.info("Returning a student, {} students left in the list.", studentRepos.size() - 1);
            return studentRepos.remove(0); // Remove and return the first student
        } else {
            return null; // Return null if no data is available
        }
    }
    
    public StudentRepo repo(Employee employee) {
    	RestTemplate restTemplate=new RestTemplate();
    	return	 restTemplate.postForObject("http://localhost:8081/c", employee, StudentRepo.class);
    	 
    }
}

