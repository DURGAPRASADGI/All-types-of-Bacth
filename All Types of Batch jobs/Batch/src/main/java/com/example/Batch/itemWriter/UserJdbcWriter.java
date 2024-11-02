package com.example.Batch.itemWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.Batch.model.UserJDBC;

@Component
public class UserJdbcWriter  implements ItemWriter<UserJDBC>{

	@Override
	public void write(Chunk<? extends UserJDBC> chunk) throws Exception {
		// TODO Auto-generated method stub
		
		for(UserJDBC userJDBC:chunk) {
			System.out.println(userJDBC);
		}
		
	}

}
