package com.juanex.customer_service.service;

import com.juanex.customer_service.dto.CustomerRequest;
import com.juanex.customer_service.dto.CustomerResponse;
import com.juanex.customer_service.entity.Customer;
import com.juanex.customer_service.exception.ResourceNotFoundException;
import com.juanex.customer_service.repository.CustomerRepository;
import com.juanex.customer_service.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    @Test
    void create_WhenCustomerRequestIsValid_ShouldReturnCustomerResponse() {
        // Arrange
        CustomerRequest request = new CustomerRequest(
                "Juan Perez",
                "juan@gmail.com"
        );

        Customer customerToSave = Customer.builder()
                .name(request.name())
                .email(request.email())
                .build();

        Customer savedCustomer = Customer.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@gmail.com")
                .build();

        when(repository.save(any(Customer.class)))
                .thenReturn(savedCustomer);

        // Act
        CustomerResponse response = service.create(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Juan Perez", response.name());
        assertEquals("juan@gmail.com", response.email());

        verify(repository, times(1)).save(any(Customer.class));
    }

    @Test
    void findAll_WhenCustomersExist_ShouldReturnCustomerResponseList() {
        // Arrange
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Juan")
                .email("juan@gmail.com")
                .build();

        Customer customer2 = Customer.builder()
                .id(2L)
                .name("Carlos")
                .email("carlos@gmail.com")
                .build();

        when(repository.findAll())
                .thenReturn(List.of(customer1, customer2));

        // Act
        List<CustomerResponse> response = service.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Juan", response.get(0).name());
        assertEquals("Carlos", response.get(1).name());

        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_WhenCustomerExists_ShouldReturnCustomerResponse() {
        // Arrange
        Long customerId = 1L;

        Customer customer = Customer.builder()
                .id(customerId)
                .name("Juan Perez")
                .email("juan@gmail.com")
                .build();

        when(repository.findById(customerId))
                .thenReturn(Optional.of(customer));

        // Act
        CustomerResponse response = service.findById(customerId);

        // Assert
        assertNotNull(response);
        assertEquals(customerId, response.id());
        assertEquals("Juan Perez", response.name());
        assertEquals("juan@gmail.com", response.email());

        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void findById_WhenCustomerDoesNotExist_ShouldThrowException() {
        // Arrange
        Long customerId = 99L;

        when(repository.findById(customerId))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(customerId)
        );

        assertEquals("Customer not found", exception.getMessage());

        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void delete_WhenCustomerExists_ShouldDeleteCustomer() {
        // Arrange
        Long customerId = 1L;

        Customer customer = Customer.builder()
                .id(customerId)
                .name("Juan Perez")
                .email("juan@gmail.com")
                .build();

        when(repository.findById(customerId))
                .thenReturn(Optional.of(customer));

        doNothing().when(repository).delete(customer);

        // Act
        service.delete(customerId);

        // Assert
        verify(repository, times(1)).findById(customerId);
        verify(repository, times(1)).delete(customer);
    }

    @Test
    void delete_WhenCustomerDoesNotExist_ShouldThrowException() {
        // Arrange
        Long customerId = 99L;

        when(repository.findById(customerId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.delete(customerId)
        );

        verify(repository, times(1)).findById(customerId);
        verify(repository, never()).delete(any(Customer.class));
    }
}
