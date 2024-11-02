package com.example.Batch.itemProcessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.Batch.model.Employee;

@Component
public class EmployeeProcessor implements ItemProcessor<Employee, Employee>{

	@Override
	public Employee process(Employee item) throws Exception {
		// TODO Auto-generated method stub
		if(item.getId()==4) {
			throw new NullPointerException();
		}
		return item;
	}

	


}
