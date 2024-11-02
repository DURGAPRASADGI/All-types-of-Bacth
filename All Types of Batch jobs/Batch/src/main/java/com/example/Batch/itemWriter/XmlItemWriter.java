package com.example.Batch.itemWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.Batch.model.Student;

@Component
public class XmlItemWriter implements ItemWriter<Student> {

	@Override
	public void write(Chunk<? extends Student> chunk) throws Exception {
		// TODO Auto-generated method stub
		for(Student student:chunk) {
			System.out.println(student);
		}
		
	}

}
