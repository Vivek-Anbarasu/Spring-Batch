package com.springbatch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.util.ApplicationUtil;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "mariaDBEntityManager",
transactionManagerRef = "mariaDBTransactionManager")
public class MariaDBConfig {

	@Bean(name = "mariaDBDataSource")
	@ConfigurationProperties(prefix = "spring.maria")
	public DataSource createmariaDBDataSource() {
		System.setProperty("spring.maria.jdbcUrl", ApplicationUtil.getMariaURL());
		System.setProperty("spring.maria.username",  ApplicationUtil.getMariaUsername());
		System.setProperty("spring.maria.password",  ApplicationUtil.getMariaPasssword());
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "mariaDBTransactionManager")
	public PlatformTransactionManager mariaDBTransactionManager(
			@Qualifier("mariaDBEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return jpaTransactionManager;
	}
	
	@Bean(name = "mariaDBEntityManager")
	public LocalContainerEntityManagerFactoryBean entityanagerFactory(@Qualifier("mariaDBDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.springbatch");
        factoryBean.setPersistenceUnitName("mariaDBPersistenceUnit");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return factoryBean;
    }

}
