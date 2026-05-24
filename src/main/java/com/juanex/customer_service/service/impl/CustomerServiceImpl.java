package com.juanex.customer_service.service.impl;

import com.juanex.customer_service.dto.CustomerRequest;
import com.juanex.customer_service.dto.CustomerResponse;
import com.juanex.customer_service.entity.Customer;
import com.juanex.customer_service.exception.ResourceNotFoundException;
import com.juanex.customer_service.repository.CustomerRepository;
import com.juanex.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    @Override
    public CustomerResponse create(CustomerRequest request) {

        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .build();

        Customer saved = repository.save(customer);

        return new CustomerResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }

    @Override
    public List<CustomerResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail()
                ))
                .toList();
    }

    @Override
    public CustomerResponse findById(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

    @Override
    public void delete(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        repository.delete(customer);
    }
}
