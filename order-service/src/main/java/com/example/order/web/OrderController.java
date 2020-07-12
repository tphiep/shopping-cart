package com.example.order.web;

import com.example.domain.OrderDto;
import com.example.order.domain.OrderService;
import com.example.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method= RequestMethod.POST)
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderDto order = orderService.createOrder(ConvertUtils.convert(createOrderRequest)).get();
        return new CreateOrderResponse(order.getOrderId());
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<OrderDto> find(@PathVariable("id") long id){
        return orderService.getOrder(id)
                .map(orderDto -> ResponseEntity.ok(orderDto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
