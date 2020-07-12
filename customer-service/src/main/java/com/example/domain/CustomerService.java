package com.example.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer create(CustomerInfo info) {
        Customer customer = Customer.create(info);
        customerRepository.save(customer);
        return customer;
    }

    public Optional<Customer> findById(long customerId) {
        return customerRepository.findById(customerId);
    }
}
