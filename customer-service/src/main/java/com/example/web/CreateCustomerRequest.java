package com.example.web;

import com.example.domain.CustomerInfo;

public class CreateCustomerRequest {

    private CustomerInfo info;

    public CreateCustomerRequest() {}

    public CreateCustomerRequest(CustomerInfo info) {
        this.info = info;
    }

    public CustomerInfo getInfo() {
        return info;
    }

    public void setInfo(CustomerInfo info) {
        this.info = info;
    }
}
