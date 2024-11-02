package com.example.Batch.itemWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FristItemWritter  implements ItemWriter<Long>{

	@Override
	public void write(Chunk<? extends Long> chunk) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("=============writer===================");
				for(Long item:chunk) {
					System.out.println(item);
				}
		
		
	}

}
