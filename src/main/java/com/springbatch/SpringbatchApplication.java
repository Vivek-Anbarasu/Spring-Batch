package com.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SpringbatchApplication implements ApplicationRunner{

	 @Autowired
	    private Job  postGresJob;

	    @Autowired
	    private Job mariaJob;

	    @Autowired
	    private JobLauncher jobLauncher;
	    
//	    @Autowired
//	    PostGresDBBatch postGresDBBatch;
//	    
//	    @Autowired
//	    MariaDBBatch mariaDBBatch ;

	    @Scheduled(cron = "${batch.postgres}")  
	    public void runPostGres() throws Exception {
	        JobParameters jobParameters = new JobParametersBuilder() .addString("jobID", String.valueOf(System.currentTimeMillis())) .toJobParameters();
	        jobLauncher.run(postGresJob, jobParameters);
	    }

	    @Scheduled(cron = "${batch.maria}")  
	    public void runMaria() throws Exception {
	        JobParameters jobParameters = new JobParametersBuilder() .addString("jobID", String.valueOf(System.currentTimeMillis())) .toJobParameters();
	        jobLauncher.run(mariaJob, jobParameters);
	    }

	
	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		postGresDBBatch.getAllTasks();
		
	}

}
