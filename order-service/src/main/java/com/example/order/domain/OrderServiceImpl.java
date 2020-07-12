package com.example.order.domain;

import com.example.domain.OrderDto;
import com.example.sagas.createorder.CreateOrderSaga;
import com.example.sagas.createorder.CreateOrderSagaData;
import com.example.utils.ConvertUtils;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SagaInstanceFactory sagaInstanceFactory;

    @Autowired
    private CreateOrderSaga createOrderSaga;

    public OrderServiceImpl(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
        this.orderRepository = orderRepository;
        this.sagaInstanceFactory = sagaInstanceFactory;
        this.createOrderSaga = createOrderSaga;
    }

    @Transactional
    public Optional<OrderDto> createOrder(final OrderDto orderDto) {
        CreateOrderSagaData data = new CreateOrderSagaData(orderDto);
        sagaInstanceFactory.create(createOrderSaga, data);
        Optional<Order> order = orderRepository.findById(data.getOrderId());
        Optional<OrderDto> dto = order.map(ConvertUtils::toOrderDto).orElse(Optional.empty());
        return dto;
    }

    public Optional<OrderDto> getOrder(long id) {
        Optional<OrderDto> orderDto =  orderRepository.findById(id).map(ConvertUtils::toOrderDto).orElse(Optional.empty());
        return orderDto;
    }


}
