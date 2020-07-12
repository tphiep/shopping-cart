package com.example.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto implements Serializable {
    private Long customerId;
    private List<ItemLine> itemLines = new ArrayList<>();
    private Long orderId;
    private String state;
    private String rejectReason;

    public OrderDto() {}

    public OrderDto(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void addItem(ItemLine item) {
        this.itemLines.add(item);
    }

    public List<ItemLine> getItemLines() {
        return itemLines;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
