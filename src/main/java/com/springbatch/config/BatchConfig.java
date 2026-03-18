package com.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    MariaDBBatch mariaDBBatch;

    @Bean
    public JobRepository jobRepository(
            @Qualifier("postgresDataSource") DataSource dataSource,
            @Qualifier("batchTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public Job postGresJob(JobRepository jobRepository, @Qualifier("postGresStep") Step postGresStep) {
        return new JobBuilder("postGresJob", jobRepository).start(postGresStep).build();
    }

    @Bean
    public Step postGresStep(JobRepository jobRepository,
            @Qualifier("postgresTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("postGresStep", jobRepository).tasklet((stepContribution, chunkContext) -> {
            postGresDBBatch.getAllTasks();
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Job mariaJob(JobRepository jobRepository, @Qualifier("mariaStep") Step mariaStep) {
        return new JobBuilder("mariaJob", jobRepository).start(mariaStep).build();
    }

    @Bean
    public Step mariaStep(JobRepository jobRepository,
            @Qualifier("mariaDBTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("mariaStep", jobRepository).tasklet((stepContribution, chunkContext) -> {
            mariaDBBatch.getMariaTables();
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

}
