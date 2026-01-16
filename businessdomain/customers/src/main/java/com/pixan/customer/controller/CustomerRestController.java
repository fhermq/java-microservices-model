/**
 * 
 */
package com.pixan.customer.controller;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.pixan.customer.entities.Customer;
import com.pixan.customer.repository.CustomerRepository;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

/**
 * 
 */

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

	@Autowired
	CustomerRepository customerRepository;

	private final WebClient.Builder webClientBuilder;

	public CustomerRestController(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;

	}

	HttpClient client = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
			.option(ChannelOption.SO_KEEPALIVE, true).option(EpollChannelOption.TCP_KEEPIDLE, 300)
			.option(EpollChannelOption.TCP_KEEPINTVL, 60).responseTimeout(Duration.ofSeconds(1))
			.doOnConnected(connection -> {
				connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
				connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
			});

	@GetMapping()
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Customer> post(@RequestBody Customer customer) {
		customer.getProducts().forEach(x -> x.setCustomer(customer));

		Customer saveCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(saveCustomer);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			return new ResponseEntity<Customer>(optional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable long id, @RequestBody Customer input) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			Customer existCustomer = optional.get();
			existCustomer.setNames(input.getNames());
			existCustomer.setPhone(input.getPhone());
			Customer saveCustomer = customerRepository.save(existCustomer);
			return new ResponseEntity<Customer>(saveCustomer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			customerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/full")
	public Customer getByCode(@RequestParam String code) {
		Customer customer = customerRepository.findByCode(code);
		customer.getProducts().forEach(product -> {
			product.setProductName(getProductName(product.getId()));
		});
		return customer;
	}

	private String getProductName(long id) {
		WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
				.baseUrl("http://localhost:8083/product")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8083/product")).build();

		JsonNode block = build.method(HttpMethod.GET).uri("/" + id).retrieve().bodyToMono(JsonNode.class).block();
		String nameString = block.get("name").asText();
		return nameString;
	}

}
