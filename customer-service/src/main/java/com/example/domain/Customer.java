package com.example.domain;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Access(AccessType.FIELD)
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private CustomerInfo customerInfo;

    public Customer(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public static Customer create(CustomerInfo info) {
        return new Customer(info);
    }
}
