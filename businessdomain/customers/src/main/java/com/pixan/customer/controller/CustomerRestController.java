/**
 * 
 */
package com.pixan.customer.controller;

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

import com.pixan.customer.entities.Customer;
import com.pixan.customer.repository.CustomerRepository;

/**
 * 
 */

@RestController
@RequestMapping("/customers")
public class CustomerRestController {
	
	@Autowired
	CustomerRepository customerRepository;

	@GetMapping()
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Customer> post(@RequestBody Customer customer) {
		Customer saveCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(saveCustomer);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			return new ResponseEntity<Customer>(optional.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable long id, @RequestBody Customer customer) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			Customer existCustomer = optional.get();
			existCustomer.setName(customer.getName());
			existCustomer.setPhone(customer.getPhone());
			Customer saveCustomer = customerRepository.save(existCustomer);
			return new ResponseEntity<Customer>(saveCustomer, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			customerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
