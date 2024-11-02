package com.example.subbatch;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
	
	@GetMapping("/all")
	public List<Student> getAllStudent(){
		return Arrays.asList(new Student(1L,"du","pr","dp@gmail.com"),
				            new Student(2L,"oou","gr","og@gmail.com"),
				            new Student(3L,"du","pr","dp@gmail.com"),
				            new Student(4L,"oou","gr","og@gmail.com"),
				            new Student(5L,"du","pr","dp@gmail.com"),
				            new Student(6L,"oou","gr","og@gmail.com"),
				            new Student(7L,"du","pr","dp@gmail.com"),
				            new Student(8L,"oou","gr","og@gmail.com"));
	}

	
	@PostMapping("/c")
	public Student createStudent(@RequestBody Student student) {
		System.out.println("cretaed student");
		System.out.println(student.getId());
		System.out.println(student.getFirstName());
		System.out.println(student.getLastName());
		System.out.println(student.getEmail());

		return student;
		
	}
}
