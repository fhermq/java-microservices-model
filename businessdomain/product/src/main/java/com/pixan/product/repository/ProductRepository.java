package com.pixan.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixan.product.entities.Product;

/**
 * @author Fernando
 * 
 */

public interface ProductRepository extends JpaRepository<Product, Long> {

}
