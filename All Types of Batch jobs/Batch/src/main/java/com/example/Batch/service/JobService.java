package com.example.Batch.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.Batch.model.FristJobModel;

@Component
public class JobService {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("fristJob")
	@Autowired
	private Job fristJob;

	@Qualifier("secoundJob")
	@Autowired
	private Job secoundJob;
	
	@Qualifier("thirdJob")
	@Autowired
	private Job thirdJob;
	
	@Qualifier("fourthJob")
	@Autowired
	private Job fourthJob;
	
	
	@Qualifier("fifthJob")
	@Autowired
	private Job fifthJob;
	
	@Qualifier("sixthJob")
	@Autowired
	private Job sixthJob;
	
	@Qualifier("jdbcJob")
	@Autowired
	private Job jdbcJob;
	
	@Qualifier("MultyJdbcJob")
	@Autowired
	private Job MultyJdbcJob;
	
	@Qualifier("restfulJob")
	@Autowired
	private Job restfulJob;
	
	@Qualifier("jpaReaderJob")
	@Autowired
	private Job jpaReaderJob;
	
	@Qualifier("jpaWriterJob")
	@Autowired
	private Job  jpaWriterJob;
	
	@Qualifier("repositoryReaderJob")
	@Autowired
	private Job repositoryReaderJob;
	
	@Qualifier("repostioryWriter")
	@Autowired
	private Job repostioryWriter;
	
     @Async
	public void startJob(String jobName,List<FristJobModel> fristJobRequest) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		//JobParameters  jobParameters=new JobParametersBuilder().addLong("currentTime", System.currentTimeMillis()).toJobParameters();
		 JobParametersBuilder  jobParameterbuoder=new JobParametersBuilder();
		fristJobRequest.stream().forEach(request->{
			jobParameterbuoder.addString(request.getParmKey(), request.getParmvalue());
		});
		
		JobParameters jobParameters=jobParameterbuoder.toJobParameters();
		
		   JobExecution jobExecution=null;
		if(jobName.equals("fristJob")) {
	    jobExecution=jobLauncher.run(fristJob, jobParameters);

			
		}else if(jobName.equals("secoundJob")) {
			 jobExecution=jobLauncher.run(secoundJob, jobParameters);

			
		}
		else if(jobName.equals("thirdJob")) {
			jobExecution=jobLauncher.run(thirdJob, jobParameters);
		}

		System.out.println("jobExecutionId "+jobExecution.getId());
		
		
	}


	public void jobRunby(String jobName, MultipartFile file) throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
	       // Save the file locally (you can customize this)

		 // Define the target directory path
        String targetDirectoryPath = "C:/temp/";
        File targetDirectory = new File(targetDirectoryPath);
        System.out.println(targetDirectoryPath);
        
        // Check if the directory exists; if not, create it
        if (!targetDirectory.exists()) {
            boolean dirCreated = targetDirectory.mkdirs();
            if (dirCreated) {
                System.out.println("Directory created: " + targetDirectoryPath);
            } else {
                System.out.println("Failed to create directory: " + targetDirectoryPath);
          
            }
        }

        // Construct the full file path where the file will be stored
        String filePath = targetDirectoryPath + file.getOriginalFilename();
        System.out.println(filePath);
        //   c:/temp:/fie
		String fileName=file.getOriginalFilename();

        File destFile = new File(filePath);
        System.out.println(destFile);

        // Save the uploaded file to the destination file
        file.transferTo(destFile); // Transfer the file content to the destination
		
		        
        JobParameters  jobParameters=new JobParametersBuilder().addString("filePath", filePath).toJobParameters();
		
        JobExecution jobExecution=null;
		
        if(jobName.equals("fristJob")) {
	    jobExecution=jobLauncher.run(fristJob, jobParameters);

			
		}else if(jobName.equals("secoundJob")) {
			 jobExecution=jobLauncher.run(secoundJob, jobParameters);

			
		}
		else if(jobName.equals("thirdJob")) {
			jobExecution=jobLauncher.run(thirdJob, jobParameters);
		}
		else if(jobName.equals("fourthJob")) {
			jobExecution=jobLauncher.run(fourthJob, jobParameters);
		}
		else if(jobName.equals("fifthJob")) {
			jobExecution=jobLauncher.run(fifthJob, jobParameters);
		}

		else if(jobName.equals("sixthJob")) {
			System.out.println("=====================");
			jobExecution=jobLauncher.run(sixthJob, jobParameters);
		}
        
		System.out.println("jobExecutionId "+jobExecution.getId());
        
		
	}


	public void jdbcJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		JobParameters jobParameters=new JobParametersBuilder().addLong("current", System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(MultyJdbcJob, jobParameters);
		
	}


	public void restfulJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		JobParameters jobParameters=new JobParametersBuilder().addLong("current", System.currentTimeMillis()).toJobParameters();
	jobLauncher.run(restfulJob, jobParameters);
	
	}


	public void filesJob(String jobName, MultipartFile file) throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		
		String outputFilePath="C:\\Users\\DURGA PRASAD\\Downloads\\Batch\\Batch\\Writer\\h.json";
		String FilePath="C:/temp/";
		File file2=new File(FilePath);
		
		if(!file2.exists()) {
		boolean fileDir=	file2.mkdir();
			if(fileDir) {
				System.out.println("folder  created");

			}else {
				System.out.println("folder doesnot created");
			}
		}
		String fileName=file.getOriginalFilename();
		String inputFilepath=FilePath+fileName;
		File file3=new File(inputFilepath);
		file.transferTo(file3);
		
//		Map<String, String> map=new HashMap<>();
//		map.put("filePath", inputFilepath);
//		map.put("outputPath", outputFilePath);
		JobParameters jobParameters=new JobParametersBuilder().addString("filePath", inputFilepath)		
			              .addString("outputPath", outputFilePath)
				              .toJobParameters();
//            JobParameters jobParameters=null;
////		
////		JobExecution jobExecution=null;
//		for(Map.Entry<String , String> paths:map.entrySet()) {
//			
//			jobParametersBuilder=jobParametersBuilder.addString(paths.getKey(), paths.getValue());
//			
//			}
//		jobParameters=jobParametersBuilder.toJobParameters();
//		if(jobName.equals("thirdJob")) {
		JobExecution	jobExecution=jobLauncher.run(thirdJob, jobParameters);

		//}
		System.out.println("jobExecution"+jobExecution.getId());
		 
		
	}


	public void jpaReaderJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		
		String Path="C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\Reader\\jpa3.csv";
		JobParameters jobParameters=new JobParametersBuilder().addString("filePath", Path).toJobParameters();
		JobExecution execution=jobLauncher.run(jpaReaderJob, jobParameters);
		System.out.println("execution "+execution.getId());
		
		
		
	}


	public void jpaWriterJob(String jobName, MultipartFile file) throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		String folder="C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\InComming\\";
		File file2=new File(folder);
		if(!file2.exists()) {
			boolean created=file2.mkdir();
			if(created) {
				System.out.println("file was created");
			}else {
				System.out.println("file was not created");
			}
		}
		
		String fileName=file.getOriginalFilename();
		String path=folder+fileName;
		File file3=new File(path);
		file.transferTo(file3);
		
		JobParameters jobParameters=new JobParametersBuilder().addString("filePath", path).toJobParameters();
		JobExecution execution=jobLauncher.run(jpaWriterJob, jobParameters);
		System.out.println("execution "+execution.getId());
		
		
	}


	public void repositoryReaderJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		String path="C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\Reader\\repo.csv";
		
		JobParameters jobParameters=new JobParametersBuilder().addString("filePath", path).toJobParameters();
		JobExecution execution=jobLauncher.run(repositoryReaderJob, jobParameters);
		System.out.println("execution "+execution.getId());
		
	}


	public void repositoryWriterJob(String jobName, MultipartFile file) throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		
		String folder="C:\\Users\\DURGA PRASAD\\Pictures\\Skip\\InComming\\";
		File file2=new File(folder);
		if(!file2.exists()) {
			boolean created=file2.mkdir();
			if(created) {
				System.out.println("folder was created");
			}else {
				System.out.println("folder not created");
			}
		}
		
		String fileName=file.getOriginalFilename();
		String path=folder+fileName;
		File file3=new File(path);
		file.transferTo(file3);
		
		JobParameters jobParameters=new JobParametersBuilder().addString("filePath", path).toJobParameters();
		JobExecution execution=jobLauncher.run(repostioryWriter, jobParameters);
		System.out.println("execution"+execution.getId());
	}

}
