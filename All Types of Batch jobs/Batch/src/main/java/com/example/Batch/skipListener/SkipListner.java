package com.example.Batch.skipListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import com.example.Batch.model.Employee;

@Component
public class SkipListner {
	
	String skipReaderPath = "C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\Batch\\Reader\\gh.txt";
	String skipProcessorPath="C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\Batch\\processor\\k.txt";
	String skipWriterPath="C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\Batch\\writer\\cc.txt";
	
	@OnSkipInProcess
public void skipProcessor(Employee employee,Throwable th) {
		createFile(skipProcessorPath, employee.toString());
	}
	@OnSkipInWrite
public void skipWriter(Employee employee,Throwable th) {
	createFile(skipWriterPath, employee.toString());
}
	
    @OnSkipInRead
    public void skipReader(Throwable th) throws IOException {
        if (th instanceof FlatFileParseException) {
            createFile(skipReaderPath, ((FlatFileParseException) th).getInput());
        }
    }

    public void createFile(String skipReaderPath, String input) {
        // Use try-with-resources to ensure FileWriter is properly closed
        try (FileWriter fileWriter = new FileWriter(new File(skipReaderPath), true)) {
            fileWriter.write(input + " "+ new Date()+"\n");  // Write the skipped record with a newline
           // fileWriter.flush();  // Ensure data is flushed to the file
            System.out.println("Skipped record written to file: " + input);  // For debugging
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}
