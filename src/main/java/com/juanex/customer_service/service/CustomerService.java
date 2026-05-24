package com.juanex.customer_service.service;

import com.juanex.customer_service.dto.CustomerRequest;
import com.juanex.customer_service.dto.CustomerResponse;
import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest request);

    List<CustomerResponse> findAll();

    CustomerResponse findById(Long id);

    void delete(Long id);
}
