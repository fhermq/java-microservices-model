package com.pixan.customer.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

/**
 * 
 * */

@Data
@Entity
public class Customer {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private String names;
	private String surname;
	private String phone;
	private String code;
	private String iban;
	private String address;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomerProduct> products;
	
	@Transient
	private List<?> transactions;
	
	
	
}
