package com.example.Batch.itemWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.Batch.model.Employee;

@Component
public class FlatItemWriter  implements ItemWriter<Employee>{

	@Override
	public void write(Chunk<? extends Employee> chunk) throws Exception {
		// TODO Auto-generated method stub
		for(Employee employee:chunk) {
			System.out.println(employee);
		}
		
	}

}
