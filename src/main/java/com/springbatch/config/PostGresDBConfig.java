package com.springbatch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.util.ApplicationUtil;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "postgresEntityManager",
transactionManagerRef = "postgresTransactionManager")
public class PostGresDBConfig {

	@Primary
	@Bean(name = "postgresDataSource")
	@ConfigurationProperties(prefix = "spring.postgres")
	public DataSource postgresDataSource() {
		System.setProperty("spring.postgres.jdbcUrl", ApplicationUtil.getURL());
		System.setProperty("spring.postgres.username",  ApplicationUtil.getUsername());
		System.setProperty("spring.postgres.password",  ApplicationUtil.getPasssword());
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "postgresTransactionManager")
	public PlatformTransactionManager postgresTransactionManager(
			@Qualifier("postgresEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return jpaTransactionManager;
	}
	
	@Primary
	@Bean(name = "postgresEntityManager")
	public LocalContainerEntityManagerFactoryBean postgresEntityManager(@Qualifier("postgresDataSource") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setPackagesToScan("com.springbatch");
		factoryBean.setPersistenceUnitName("postgresPersistenceUnit");
		factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return factoryBean;
	}

	@Primary
	@Bean(name = "postgresJdbcTemplate")
	public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
