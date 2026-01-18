package com.springbatch.application;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


@Component
public class PostGresDBBatch {
	
	@PersistenceContext(unitName = "postgresPersistenceUnit")
	EntityManager entityManager;

	 @SuppressWarnings("unchecked")
	 public void getAllTasks() {
		 Query query = entityManager.createNativeQuery("SELECT status FROM tasks");
		 List<String> listofTasks = query.getResultList();
		 for(String  status:listofTasks) {
			 System.out.println(status);
		 }
	}
}
