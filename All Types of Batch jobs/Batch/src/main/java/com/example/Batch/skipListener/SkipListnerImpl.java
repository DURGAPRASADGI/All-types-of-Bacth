package com.example.Batch.skipListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import com.example.Batch.model.Employee;

@Component
public class SkipListnerImpl implements SkipListener<Employee, Employee>{
	String skipReaderPath = "C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\Batch\\Reader\\gh.txt";
	String skipProcessorPath="C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\Batch\\processor\\k.txt";
	String skipWriterPath="C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\Batch\\writer\\cc.txt";
	
	@Override
	public void onSkipInRead(Throwable t) {
		// TODO Auto-generated method stub
		try {
			createFile(skipReaderPath, ((FlatFileParseException)t).getInput());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSkipInWrite(Employee item, Throwable t) {
		// TODO Auto-generated method stub
		try {
			createFile(skipProcessorPath, item.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSkipInProcess(Employee item, Throwable t) {
		// TODO Auto-generated method stub
		try {
			createFile(skipWriterPath, item.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createFile(String path,String data) throws IOException {
		
		try(FileWriter fileWriter=new FileWriter(new File(path),true)) {
			fileWriter.write(data +"\n");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
