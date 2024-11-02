package com.example.Batch.JobController;

import java.io.IOException;
import java.util.List;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Batch.model.FristJobModel;
import com.example.Batch.service.JobService;
import com.example.Batch.service.WriterService;

@RestController
@RequestMapping("/api/job")

public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private JobOperator jobOperator;
	
	@Autowired
	private WriterService writerService;
	
	@GetMapping
	public String jdbcJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		jobService.jdbcJob();
		return "job started";
		
	}
	
	
	@GetMapping("/restful")
	public String restfulJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		jobService.restfulJob();
		return "job started";
		
	}
	
	
	@GetMapping("/jobName/{jobName}")
	public String jobRun(@PathVariable String jobName,
			@RequestBody List<FristJobModel> fristJobRequest) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		jobService.startJob(jobName,fristJobRequest);
		
		return "job started";
		
	}
	
	@PostMapping("/jobName/{jobName}")
	public String jobRunby(@PathVariable String jobName,
			@RequestParam("file") MultipartFile file ) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IllegalStateException, IOException {
		jobService.jobRunby(jobName,file);
		
		return "job started";
		
	}
	
	@GetMapping("/writer/{jobName}")
	public String filewriter(@PathVariable String jobName) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		writerService.filewriter(jobName);
		return "jon started";
		
	}
	
	@GetMapping("/{executedId}")
	public String jobStoped(@PathVariable long executedId) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
		jobOperator.stop(executedId);
		return "job completed...";
		
	}
	
	@GetMapping("/file/{jobName}")
	public String filesJob(@PathVariable String jobName,@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		jobService.filesJob(jobName,file);
		return "job started";
	}

	@GetMapping("/file")
	public String jpaReaderJob() throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		jobService.jpaReaderJob();
		return "job started";
	}
	
	@GetMapping("/jpaWriterJob/{jobName}")
	public String jpaWriterJob(@PathVariable String jobName,@RequestParam("file") MultipartFile file) throws IllegalStateException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		jobService.jpaWriterJob(jobName,file);
		return "job started";

	}
	@GetMapping("/repositoryReaderJob")
	public String repositoryReaderJob() throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		jobService.repositoryReaderJob();
		return "job started";
	}
	
	@GetMapping("/repositoryWriterJob/{jobName}")
	public String repositoryWriterJob(@PathVariable String jobName,@RequestParam("file") MultipartFile file) throws IllegalStateException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		jobService.repositoryWriterJob(jobName,file);
		return "job started";

	}
}
