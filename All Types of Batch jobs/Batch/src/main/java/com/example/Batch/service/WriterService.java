package com.example.Batch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class WriterService {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("MultyJdbcJob")
	@Autowired
	private Job MultyJdbcJob;
	
	String destinationFile="C:\\Users\\DURGA PRASAD\\Downloads\\Batch\\Batch\\Writer\\lll.xml";
			

	public void filewriter(String jobName) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		JobParameters jobParameters=new JobParametersBuilder().addString("filePath", destinationFile).toJobParameters();
		
		JobExecution execution=null;
		if(jobName.equals("MultyJdbcJob")) {
			execution=jobLauncher.run(MultyJdbcJob, jobParameters);
		}
		System.out.println("execution "+execution.getId());
		
		
	}
	
	

}
