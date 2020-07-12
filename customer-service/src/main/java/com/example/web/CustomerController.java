package com.example.web;

import com.example.domain.Customer;
import com.example.domain.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/customers")
public class CustomerController {

    @Autowired
    private CustomerService consumerService;

    @RequestMapping(method= RequestMethod.POST)
    public CreateCustomerResponse create(@RequestBody CreateCustomerRequest request) {
        Customer result = consumerService.create(request.getInfo());
        return new CreateCustomerResponse(result.getId());
    }
}
