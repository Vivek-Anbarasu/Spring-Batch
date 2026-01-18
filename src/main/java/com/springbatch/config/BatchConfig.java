package com.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.application.MariaDBBatch;
import com.springbatch.application.PostGresDBBatch;

@Configuration
public class BatchConfig {
	
    @Autowired
    PostGresDBBatch postGresDBBatch;
    
    @Autowired
    MariaDBBatch mariaDBBatch ;

	@Bean
    public Job postGresJob(JobRepository jobRepository, Step postGresStep) {
        return new JobBuilder("postGresJob", jobRepository).start(postGresStep) .build();
    }

    @Bean
    public Step postGresStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("postGresStep", jobRepository).tasklet((stepContribution, chunkContext) -> {
        	  postGresDBBatch.getAllTasks();
              return RepeatStatus.FINISHED;
          }, transactionManager) .build();
    }

    @Bean
    public Job mariaJob(JobRepository jobRepository, Step mariaStep) {
        return new JobBuilder("mariaJob", jobRepository).start(mariaStep) .build();
    }

    @Bean
    public Step mariaStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("mariaStep", jobRepository).tasklet((stepContribution, chunkContext) -> {
        	mariaDBBatch.getMariaTables();
              return RepeatStatus.FINISHED;
          }, transactionManager) .build();
    }

 
}
