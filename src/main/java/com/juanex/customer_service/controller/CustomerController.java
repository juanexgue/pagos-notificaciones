package com.juanex.customer_service.controller;

import com.juanex.customer_service.dto.CustomerRequest;
import com.juanex.customer_service.dto.CustomerResponse;
import com.juanex.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public CustomerResponse create(
            @Valid @RequestBody CustomerRequest request) {

        return service.create(request);
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(
            @PathVariable Long id) {

        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }
}
