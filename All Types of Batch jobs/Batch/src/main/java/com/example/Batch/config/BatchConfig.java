package com.example.Batch.config;

import java.io.IOException;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

import com.example.Batch.entity.Order;
import com.example.Batch.iteamReader.FristIteamReader;
import com.example.Batch.itemProcessor.EmployeeProcessor;
import com.example.Batch.itemProcessor.FristItemProcessor;
import com.example.Batch.itemProcessor.JsonProcessor;
import com.example.Batch.itemWriter.FlatItemWriter;
import com.example.Batch.itemWriter.FristItemWritter;
import com.example.Batch.itemWriter.SalesWritter;
import com.example.Batch.itemWriter.StudentRepoWriter;
import com.example.Batch.itemWriter.UserJdbcWriter;
import com.example.Batch.itemWriter.XmlItemWriter;
import com.example.Batch.model.Employee;
import com.example.Batch.model.Student;
import com.example.Batch.model.StudentRepo;
import com.example.Batch.model.UserJDBC;
import com.example.Batch.mysqlrepository.OrderRepository;
import com.example.Batch.postgrerepo.PostgreRepository;
import com.example.Batch.service.StudentService;
import com.example.Batch.skipListener.SkipListner;
import com.example.Batch.skipListener.SkipListnerImpl;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@AllArgsConstructor
public class BatchConfig {

//  Logger logger=org.slf4j.Logger.\\\\\\\\\
	private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

	private FristStepListner fristStepListner;

	private StudentRepoWriter studentRepoWriter;

	private FristJobListner fristJobListner;

	private FristIteamReader fristIteamReader;

	private FristItemProcessor fristItemProcessor;

	private FristItemWritter fristItemWritter;

	private SalesWritter salesWritter;

	private EmployeeProcessor employeeProcessor;

	private SkipListnerImpl impl;

	private FlatItemWriter flatItemWriter;

	private XmlItemWriter xmlItemWriter;

//	@Autowired
//	private ExcelItemReader excelItemReader;

	private UserJdbcWriter jdbcWriter;

	private StudentService studentService;
	private JsonProcessor jsonProcessor;

	private SkipListner skipListner;

	private MultipleDataSources source;
	
	 private PostgreRepository postgreRepository;
	 
	 private OrderRepository orderRepository;

	@Bean
	@StepScope
	public FlatFileItemWriter<Saleman> fileItemWriter(
			@Value("#{jobParameters['filePath']}") FileSystemResource fileSystemResource) {

		System.out.println(fileSystemResource);
		FlatFileItemWriter<Saleman> fileItemWriter = new FlatFileItemWriter<>();
		fileItemWriter.setResource(fileSystemResource);
		FlatFileHeaderCallback headerCallback = new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				// TODO Auto-generated method stub
				writer.write("sales Id,name,city,commission");

			}
		};
		fileItemWriter.setHeaderCallback(headerCallback);

		DelimitedLineAggregator<Saleman> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<Saleman> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] { "salesId", "name", "city", "commission" });
		aggregator.setFieldExtractor(fieldExtractor);
		fileItemWriter.setLineAggregator(aggregator);

		FlatFileFooterCallback fileFooterCallback = new FlatFileFooterCallback() {

			@Override
			public void writeFooter(Writer writer) throws IOException {
				// TODO Auto-generated method stub
				writer.append("created @ " + new Date());

			}
		};
		fileItemWriter.setFooterCallback(fileFooterCallback);

		return fileItemWriter;

	}

	@Bean
	@StepScope
	public JsonFileItemWriter<JsonWriter> jsonItemWriter(
			@Value("#{jobParameters['outputPath']}") FileSystemResource fileSystemResource) {
		System.out.println(fileSystemResource);
		JsonFileItemWriter<JsonWriter> jsonFileItemWriter = new JsonFileItemWriter<JsonWriter>(fileSystemResource,
				new JacksonJsonObjectMarshaller<JsonWriter>()) {
			@Override
			public String doWrite(Chunk<? extends JsonWriter> items) {
				// TODO Auto-generated method stub
				for (JsonWriter item : items) {
					if (item.getSalesId() == 2) {
						throw new NullPointerException();
					}
				}
				return super.doWrite(items);
			}
		};
		jsonFileItemWriter.setResource(fileSystemResource);

		return jsonFileItemWriter;

	}

	public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1", jobRepository).tasklet(fristTasklet(), transactionManager)
				.listener(fristStepListner).build();
	}

	public Tasklet fristTasklet() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				logger.info("==========fristTasklet===========================");
				return RepeatStatus.FINISHED;
			}
		};

	}

	Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step2", jobRepository).tasklet(secoundTasklet(), transactionManager)
				.listener(fristStepListner).build();
	}

	public Tasklet secoundTasklet() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				logger.info("=============secoundTasklet========================");
				return RepeatStatus.FINISHED;
			}
		};
	}

	@Bean
	Job fristJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
		return new JobBuilder("job", jobRepository).incrementer(new RunIdIncrementer())
				.start(step1(jobRepository, transactionManager)).next(step2(jobRepository, transactionManager))
				.listener(fristJobListner).build();
	}

	@Bean
	public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep", jobRepository).<Integer, Long>chunk(3, transactionManager) // Processing in
																										// chunks of 3
				.reader(fristIteamReader).processor(fristItemProcessor).writer(fristItemWritter).build();
	}

	@Bean
	Job secoundJob(JobRepository jobRepository, PlatformTransactionManager manager) {
		return new JobBuilder("secound job", jobRepository)

				.incrementer(new RunIdIncrementer()).start(chunkStep(jobRepository, manager))
				.next(step2(jobRepository, manager))

				.build();
	}

	@Bean
	// @StepScope
	@JobScope
	public FlatFileItemReader<Employee> fileItemReader(
			@Value("#{jobParameters['filePath']}") String fileSystemResource) {
		// Create FileSystemResource with the file path
		FileSystemResource fileResource = new FileSystemResource(fileSystemResource);
		FlatFileItemReader<Employee> fileItemReader = new FlatFileItemReader<>();

		// Set the resource to read the CSV file
		fileItemReader.setResource(fileResource);
		fileItemReader.setStrict(false);

		fileItemReader.setLinesToSkip(1);
		DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames("id", "firstName", "lastName", "email");
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
//			 {
//				 setTargetType(Employee.class);
//			 }
//			
			@Override
			public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
				// TODO Auto-generated method stub
				// int id=Integer.valueOf(fieldSet.readInt("id"));
				Employee employee = new Employee(fieldSet.readInt("id"), fieldSet.readString("firstName"),
						fieldSet.readString("lastName"), fieldSet.readString("email"));
				return employee;
			}
		});
		fileItemReader.setLineMapper(defaultLineMapper);

		return fileItemReader;

	}

	@Bean
	public JdbcBatchItemWriter<Employee> batchItemWriter() {
		JdbcBatchItemWriter<Employee> batchItemWriter = new JdbcBatchItemWriter<>();
		batchItemWriter.setDataSource(source.source2());
		batchItemWriter.setSql(
				"insert into employee(id,firstName,lastName,email)" + "values(:id,:firstName,:lastName,:email)");
		batchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		return batchItemWriter;
	}

	@Bean
	public JdbcBatchItemWriter<Employee> batchItemWriter1() {
		JdbcBatchItemWriter<Employee> batchItemWriter = new JdbcBatchItemWriter<>();
		batchItemWriter.setDataSource(source.source2());
		batchItemWriter.setSql("insert into employee(id,firstName,lastName,email)" + "values(?,?,?,?)");
		// batchItemWriter.setItemSqlParameterSourceProvider(new
		// BeanPropertyItemSqlParameterSourceProvider<Employee>());
		batchItemWriter.setItemPreparedStatementSetter(new ItemPreparedStatementSetter<Employee>() {

			@Override
			public void setValues(Employee item, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				if (item.getId() == 3) {
					throw new NullPointerException();
				}
				ps.setLong(1, item.getId());
				ps.setString(2, item.getFirstName());
				ps.setString(3, item.getLastName());
				ps.setString(4, item.getEmail());

			}
		});
		return batchItemWriter;
	}

	@Bean
	public ItemWriterAdapter<StudentRepo> adapter() {
		ItemWriterAdapter<StudentRepo> adapter = new ItemWriterAdapter<>();
		adapter.setTargetObject(studentService);
		adapter.setTargetMethod("repo");

		return adapter;
	}

	@Bean
	public Step chunkStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep", jobRepository).<Employee, Employee>chunk(3, transactionManager) // Processing
																											// in chunks
																											// of 3
				.reader(fileItemReader(null)).processor(employeeProcessor)
				// .writer(flatItemWriter)
				// .writer(batchItemWriter())
				// .writer(jsonItemWriter(null))
				// .writer(adapter())
				.writer(batchItemWriter1()).faultTolerant().skip(Throwable.class)
				// .skip(FlatFileParseException.class)
				// .skipLimit(Integer.MAX_VALUE)
				.skipPolicy(new AlwaysSkipItemSkipPolicy())
				// .listener(skipListner)
				.listener(impl)

				.build();

	}

	@Bean
	Job thirdJob(JobRepository jobRepository, PlatformTransactionManager manager) {
		return new JobBuilder("third job", jobRepository)

				.incrementer(new RunIdIncrementer()).start(chunkStep2(jobRepository, manager))

				.build();
	}

	@StepScope
	@Bean
	public ExcelItemReader excelItemReader(@Value("#{jobParameters['filePath']}") String filePath) {
		System.out.println("Reading Excel file from path: " + filePath);
		Resource fileResource = new FileSystemResource(filePath);

		if (!fileResource.exists()) {
			throw new IllegalArgumentException("File not found at path: " + filePath);
		}

		return new ExcelItemReader(fileResource);
	}

	public Step chunkStep3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep", jobRepository).<Employee, Employee>chunk(3, transactionManager) // Processing
																											// in chunks
																											// of 3
				.reader(excelItemReader(null)).writer(flatItemWriter).build();

	}

	@Bean
	Job fourthJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("fourth job", jobRepository).incrementer(new RunIdIncrementer())
				.start(chunkStep3(jobRepository, transactionManager)).build();
	}

	// json Reader
	@Bean
	@StepScope
	JsonItemReader<Employee> jsonItemReader(@Value("#{jobParameters['filePath']}") FileSystemResource resource) {
		System.out.println("resource  " + resource);
		JsonItemReader<Employee> itemReader = new JsonItemReader<>();
		itemReader.setResource(resource);
		itemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(Employee.class));
		itemReader.setMaxItemCount(4);

		itemReader.setCurrentItemCount(3);

		return itemReader;

	}

	public Step chunkStep4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep", jobRepository).<Employee, Employee>chunk(5, transactionManager) // Processing
																											// in chunks
																											// of 3
				.reader(jsonItemReader(null)).writer(flatItemWriter).build();

	}

	@Bean
	Job fifthJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("fifthJob job", jobRepository).incrementer(new RunIdIncrementer())
				.start(chunkStep4(jobRepository, transactionManager)).build();
	}

	@Bean
	@StepScope
	StaxEventItemReader<Student> staxEventItemReader(@Value("#{jobParameters['filePath']}") String path) {
		FileSystemResource fileSystemResource = new FileSystemResource(path);
		StaxEventItemReader<Student> eventItemReader = new StaxEventItemReader<>();
		eventItemReader.setResource(fileSystemResource);
		eventItemReader.setFragmentRootElementName("student");
		eventItemReader.setUnmarshaller(convertObject());
		return eventItemReader;

	}

	@Bean
	@StepScope
	StaxEventItemWriter<Saleman> staxEventItemWriter(
			@Value("#{jobParameters['filePath']}") FileSystemResource fileSystemResource) {
		System.out.println("writer");
		StaxEventItemWriter<Saleman> staxEventItemWrite = new StaxEventItemWriter<>();
		staxEventItemWrite.setResource(fileSystemResource);
		staxEventItemWrite.setRootTagName("salesmans");
		staxEventItemWrite.setMarshaller(convertIntoMasher());

		return staxEventItemWrite;
	}

	public Jaxb2Marshaller convertIntoMasher() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(Saleman.class);

		return jaxb2Marshaller;

	}

	private Jaxb2Marshaller convertObject() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Student.class);
		return marshaller;
	}

	public Step chunkStep5(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep", jobRepository).<Student, Student>chunk(5, transactionManager) // Processing
																											// in chunks
																											// of 3
				.reader(staxEventItemReader(null)).writer(xmlItemWriter).build();

	}

	@Bean
	Job sixthJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("sixth job", jobRepository).incrementer(new RunIdIncrementer())
				.start(chunkStep5(jobRepository, transactionManager)).build();
	}

	@Bean
	JdbcCursorItemReader<UserJDBC> jdbcCursorItemReader() {
		JdbcCursorItemReader<UserJDBC> cursorItemReader = new JdbcCursorItemReader<>();
		cursorItemReader.setDataSource(source.source1());
		cursorItemReader.setSql("Select user_id,username,email,password from user");
		cursorItemReader.setRowMapper(new BeanPropertyRowMapper<UserJDBC>() {
			{
				setMappedClass(UserJDBC.class);
			}
		});
		cursorItemReader.setCurrentItemCount(2);
		cursorItemReader.setMaxItemCount(4);

		return cursorItemReader;

	}

	public Step chunkStep6(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep", jobRepository).<UserJDBC, UserJDBC>chunk(3, transactionManager) // Processing
																											// in chunks
																											// of 3
				.reader(jdbcCursorItemReader()).writer(jdbcWriter)
				// .writer(flatItemWriter)
				.build();

	}

	@Bean
	Job jdbcJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("jdbc job", jobRepository).incrementer(new RunIdIncrementer())
				.start(chunkStep6(jobRepository, transactionManager)).build();

	}

	@Bean
	@StepScope
	JdbcCursorItemReader<Saleman> jdbcCursorItemReader1() {
		System.out.println("reader has done");
		JdbcCursorItemReader<Saleman> cursorItemReader = new JdbcCursorItemReader<>();
		cursorItemReader.setDataSource(source.source2());
		cursorItemReader.setSql("Select sales_id,name,city,commission from salesman");
		cursorItemReader.setRowMapper(new BeanPropertyRowMapper<Saleman>() {
			{
				setMappedClass(Saleman.class);
			}
		});
//	cursorItemReader.setCurrentItemCount(2);
//	cursorItemReader.setMaxItemCount(4);

		return cursorItemReader;

	}

	public Step chunkStep7(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep", jobRepository).<Saleman, Saleman>chunk(3, transactionManager) // Processing
																											// in chunks
																											// of 3

				// .<Saleman, JsonWriter>chunk(3,transactionManager) // Processing in chunks of
				// 3
				.reader(jdbcCursorItemReader1())
				// .processor(jsonProcessor)
				// .writer(salesWritter)
				// .writer(fileItemWriter(null))
				.writer(staxEventItemWriter(null)).build();

	}

	@Bean
	Job MultyJdbcJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("MultyJdbcJob", jobRepository).incrementer(new RunIdIncrementer())
				.start(chunkStep7(jobRepository, transactionManager)).build();

	}

	public ItemReaderAdapter<StudentRepo> itemReaderAdapter() {
		ItemReaderAdapter<StudentRepo> adapter = new ItemReaderAdapter<>();
		adapter.setTargetObject(studentService);
		adapter.setTargetMethod("getStudent");
		// we pass some arrguments
		adapter.setArguments(new Object[] { 1L, "pp" });
		return adapter;

	}

	public Step chunkStep8(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep 8", jobRepository).<StudentRepo, StudentRepo>chunk(3, transactionManager) // Processing
																													// in
																													// chunks
																													// of
																													// 3
				.reader(itemReaderAdapter()).writer(studentRepoWriter).build();

	}

	@Bean
	Job restfulJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("restfulJob", jobRepository).incrementer(new RunIdIncrementer())
				.start(chunkStep8(jobRepository, transactionManager)).build();

	}

	@Bean
	JpaCursorItemReader<com.example.Batch.entity.Student> jpaPagingItemReader(
			@Qualifier("entityManagerFactoryBean") EntityManagerFactory entityManagerFactory) {
		JpaCursorItemReader<com.example.Batch.entity.Student> cursorItemReader = new JpaCursorItemReader<>();
		cursorItemReader.setEntityManagerFactory(entityManagerFactory);
		cursorItemReader.setQueryString("From Student");

		cursorItemReader.setSaveState(false); // Set to true if you want to save state
		System.out.println("reader ok");
		return cursorItemReader;

	}

	@Bean
	@StepScope
	public FlatFileItemWriter<com.example.Batch.entity.Student> fileItemWriter1(
			@Value("#{jobParameters['filePath']}") FileSystemResource fileSystemResource) {
		FlatFileItemWriter<com.example.Batch.entity.Student> fileItemWriter = new FlatFileItemWriter<>();
		fileItemWriter.setResource(fileSystemResource);
		FlatFileHeaderCallback fileHeaderCallback = new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				// TODO Auto-generated method stub
				writer.write("id,firstName,lastName,email,department,isActive");

			}
		};
		fileItemWriter.setHeaderCallback(fileHeaderCallback);
		DelimitedLineAggregator<com.example.Batch.entity.Student> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<com.example.Batch.entity.Student> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] { "id", "firstName", "lastName", "email", "deptId", "isActive" });
		aggregator.setFieldExtractor(fieldExtractor);
		fileItemWriter.setLineAggregator(aggregator);

		return fileItemWriter;
	}

	@Bean
	public Step chukStep9(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
		return new StepBuilder("chunk9",
				jobRepository).<com.example.Batch.entity.Student, com.example.Batch.entity.Student>chunk(10,
						transactionManager)
				.reader(jpaPagingItemReader(null)).writer(fileItemWriter1(null)).build();

	}

	@Bean
	Job jpaReaderJob(@Qualifier("platformTransactionManager") PlatformTransactionManager transactionManager,
			JobRepository jobRepository) {
		return new JobBuilder("jpaReaderJob", jobRepository).incrementer(new RunIdIncrementer())
				.start(chukStep9(transactionManager, jobRepository)).build();
	}

	@Bean
	@StepScope
	FlatFileItemReader<Order> itemReader(@Value("#{jobParameters['filePath']}") FileSystemResource fileSystemResource) {
		FlatFileItemReader<Order> fileItemReader = new FlatFileItemReader<>();
		fileItemReader.setResource(fileSystemResource);
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		fileItemReader.setStrict(false);
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames("id", "firstName", "lastName", "email", "deptId");
		fileItemReader.setLinesToSkip(1);
		DefaultLineMapper<Order> defaultLineMapper = new DefaultLineMapper<>();
		defaultLineMapper.setLineTokenizer(lineTokenizer);

		defaultLineMapper.setFieldSetMapper(new FieldSetMapper<Order>() {

			@Override
			public Order mapFieldSet(FieldSet fieldSet) throws BindException {
				// TODO Auto-generated method stub
				return new Order(fieldSet.readLong("id"), fieldSet.readString("firstName"),
						fieldSet.readString("lastName"), fieldSet.readString("email"), fieldSet.readLong("deptId"));

			}
		});
		fileItemReader.setLineMapper(defaultLineMapper);
		return fileItemReader;

	}

	@Bean
	public JpaItemWriter<Order> itemWriter(
			@Qualifier("mysqlEntityManagerFactoryBean") EntityManagerFactory entityManagerFactory) {
		JpaItemWriter<Order> jpaItemWriter = new JpaItemWriter<Order>();

		jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
		jpaItemWriter.setUsePersist(true);

		return jpaItemWriter;
	}

	@Bean
	public Step chunkStep10(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep10", jobRepository).<Order, Order>chunk(1000, transactionManager)
				.reader(itemReader(null)).writer(itemWriter(null))
				// .listener(skipListner)
				.build();
	}

	@Bean
	public Job jpaWriterJob(JobRepository jobRepository,
			@Qualifier("mysqlTransactionManager") PlatformTransactionManager platformTransactionManager) {
		return new JobBuilder("jpaWriterJob", jobRepository).incrementer(new RunIdIncrementer())
				.start(chunkStep10(jobRepository, platformTransactionManager)).build();

	}
	
	@Bean
	public RepositoryItemReader<com.example.Batch.entity.Student> repositoryItemReader(){
		RepositoryItemReader<com.example.Batch.entity.Student> repositoryItemReader=new RepositoryItemReader<>();
		repositoryItemReader.setRepository(postgreRepository);
		repositoryItemReader.setMethodName("findAll");
		Map<String, Sort.Direction> sort = new HashMap<>();
		sort.put("firstName", Sort.Direction.ASC);
		sort.put("lastName", Sort.Direction.DESC); // Example of sorting by lastName in descending order
		repositoryItemReader.setSort(sort); // Replace "id" with the appropriate field for sorting
		    repositoryItemReader.setSort(sort); // Set the sort order
		return repositoryItemReader;
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<com.example.Batch.entity.Student> flatFileItemWriter(@Value("#{jobParameters['filePath']}") FileSystemResource fileSystemResource){
		FlatFileItemWriter<com.example.Batch.entity.Student> flatFileItemWriter=new FlatFileItemWriter<>();
		flatFileItemWriter.setResource(fileSystemResource);
		FlatFileHeaderCallback fileHeaderCallback=new FlatFileHeaderCallback() {
			
			@Override
			public void writeHeader(Writer writer) throws IOException {
				// TODO Auto-generated method stub
				writer.write("id,firstName,lastName,email,deptId,isActive");
				
			}
		};
		flatFileItemWriter.setHeaderCallback(fileHeaderCallback);
		BeanWrapperFieldExtractor<com.example.Batch.entity.Student> fieldExtractor=new BeanWrapperFieldExtractor<>();
		
		fieldExtractor.setNames(new String[] {"id","firstName","lastName","email","deptId","isActive"});
		DelimitedLineAggregator<com.example.Batch.entity.Student> aggregator=new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");
		aggregator.setFieldExtractor(fieldExtractor);
		flatFileItemWriter.setLineAggregator(aggregator);
		return flatFileItemWriter;
	}
	
	@Bean
	public Step chunkStep12(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep12", jobRepository)
				.<com.example.Batch.entity.Student,com.example.Batch.entity.Student>chunk(10, transactionManager)
				.reader(repositoryItemReader())
				.writer(flatFileItemWriter(null))
				.build();
	}
	
	@Bean
	public Job repositoryReaderJob(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		
		
		return new JobBuilder("repositoryReaderJob",jobRepository).incrementer(new RunIdIncrementer()).start(chunkStep12(jobRepository,transactionManager)).build();
	
	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<Order> repositoryreader(@Value("#{jobParameters['filePath']}") FileSystemResource fileSystemResource){
		System.out.println(fileSystemResource);
		FlatFileItemReader<Order> reader=new FlatFileItemReader<>();
		reader.setResource(fileSystemResource);
		reader.setLinesToSkip(1);
		DelimitedLineTokenizer delimitedLineTokenizer=new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setNames("id","firstName","lastName","email","deptId");
		DefaultLineMapper<Order> defaultLineMapper=new DefaultLineMapper<>();
		defaultLineMapper.setFieldSetMapper(new FieldSetMapper<Order>() {
			
			@Override
			public Order mapFieldSet(FieldSet fieldSet) throws BindException {
				// TODO Auto-generated method stub
				return new Order(fieldSet.readLong("id"), fieldSet.readString("firstName"), fieldSet.readString("lastName"), fieldSet.readString("email"), fieldSet.readLong("deptId"));
			}
		});
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
	reader.setLineMapper(defaultLineMapper);
		
		
		return reader;
		
	}
	
	@Bean
	public RepositoryItemWriter<Order> repositoryItemWriter(){
		RepositoryItemWriter<Order> repositoryItemWriter=new RepositoryItemWriter<>();
		repositoryItemWriter.setRepository(orderRepository);
		repositoryItemWriter.setMethodName("save");
		
		return repositoryItemWriter;
		
	}
	
	@Bean
	public Step chunkStep13(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		return new StepBuilder("chunkStep13", jobRepository)
				.<Order,Order>chunk(10, transactionManager)
				.reader(repositoryreader(null))
				.writer(repositoryItemWriter())
				.build();
	}
	
	@Bean
	public Job repostioryWriter(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		return new JobBuilder("repostioryWriter", jobRepository).incrementer(new RunIdIncrementer()).start(chunkStep13(jobRepository, transactionManager)).build();
		
	}
}
