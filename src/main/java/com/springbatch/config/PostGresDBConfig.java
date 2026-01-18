package com.springbatch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.util.ApplicationUtil;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "postgresEntityManager",
transactionManagerRef = "postgresTransactionManager")
public class PostGresDBConfig {

	@Primary
	@Bean(name = "postgresDataSource")
	@ConfigurationProperties(prefix = "spring.postgres")
	public DataSource createPostgresDataSource() {
		System.setProperty("spring.postgres.jdbcUrl", ApplicationUtil.getURL());
		System.setProperty("spring.postgres.username",  ApplicationUtil.getUsername());
		System.setProperty("spring.postgres.password",  ApplicationUtil.getPasssword());
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "postgresTransactionManager")
	public PlatformTransactionManager postgresTransactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setDataSource(createPostgresDataSource());
		return jpaTransactionManager;
	}
	
	@Primary
	@Bean(name = "postgresEntityManager")
	public LocalContainerEntityManagerFactoryBean entityanagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("postgresDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.springbatch").persistenceUnit("postgresPersistenceUnit").build();
	}
	
}
