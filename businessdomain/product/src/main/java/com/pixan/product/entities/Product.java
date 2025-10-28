package com.pixan.product.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;



@Entity
@Data
public class Product {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private String code;
	private String name;
	private double price;
	private String currency; // @todo Should be ENUM 
	private String category;
	private int stockQuantity;
	

}
