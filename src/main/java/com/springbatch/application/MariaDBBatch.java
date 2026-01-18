package com.springbatch.application;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


@Component
public class MariaDBBatch {
    
	@PersistenceContext(unitName = "mariaDBPersistenceUnit")
	EntityManager mariaDBEntityManager;

	@SuppressWarnings("unchecked")
	public void getMariaTables() {
		 Query query = mariaDBEntityManager.createNativeQuery("SELECT Title FROM tasks");
		 List<String> listofTasks = query.getResultList();
		 for(String  status:listofTasks) {
			 System.out.println(status);
		 }
	}
	
}
