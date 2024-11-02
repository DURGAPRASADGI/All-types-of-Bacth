package com.example.Batch.itemWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.Batch.model.StudentRepo;

@Component
public class StudentRepoWriter  implements ItemWriter<StudentRepo>{

	@Override
	public void write(Chunk<? extends StudentRepo> chunk) throws Exception {
		// TODO Auto-generated method stub
		for(StudentRepo st:chunk) {
			System.out.println(st);
		}
	}

}
