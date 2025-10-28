package com.pixan.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixan.product.entities.Product;
import com.pixan.product.repository.ProductRepository;

/**
 * 
 */

@RestController
@RequestMapping("/product")
public class ProductRestController {

	@Autowired
	ProductRepository productRepository;

	@GetMapping()
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Product> post(@RequestBody Product product) {
		Product saveProduct = productRepository.save(product);
		return ResponseEntity.ok(saveProduct);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		Optional<Product> optional = productRepository.findById(id);
		if (optional.isPresent()) {
			return new ResponseEntity<Product>(optional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable long id, @RequestBody Product input) {
		Optional<Product> optional = productRepository.findById(id);
		if (optional.isPresent()) {
			Product existProduct = optional.get();
			existProduct.setCategory(input.getCategory());
			existProduct.setCode(input.getCode());
			existProduct.setCurrency(input.getCurrency());
			existProduct.setName(input.getName());
			existProduct.setPrice(input.getPrice());
			existProduct.setStockQuantity(input.getStockQuantity());
			Product saveProduct = productRepository.save(existProduct);
			return new ResponseEntity<Product>(saveProduct, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		Optional<Product> optional = productRepository.findById(id);
		if(optional.isPresent()) {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	

}
