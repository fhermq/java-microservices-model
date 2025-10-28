package com.pixan.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixan.customer.entities.Customer;
/**
 * @author Fernando
 * 
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	

}
