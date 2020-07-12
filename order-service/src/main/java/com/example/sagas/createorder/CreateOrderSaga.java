package com.example.sagas.createorder;

import com.example.commands.ReserveProductCommand;
import com.example.domain.OrderDto;
import com.example.order.domain.Order;
import com.example.order.domain.OrderRepository;
import com.example.order.domain.OrderState;
import com.example.order.domain.RejectionReason;
import com.example.replies.ProductOutOfStock;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

import java.util.Optional;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

    private OrderRepository orderRepository;

    public CreateOrderSaga(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Define Saga workflow
     */
    private SagaDefinition<CreateOrderSagaData> sagaDefinition =
                    step()
                        .invokeLocal(this::create)
                        .withCompensation(this::reject)
                    .step()
                        .invokeParticipant(this::reserveProduct)
                        .onReply(ProductOutOfStock.class, this::handleOutOfStock)
                    .step()
                        .invokeLocal(this::approve)
                    .build();

    @Override
    public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
        return this.sagaDefinition;
    }

    private void create(CreateOrderSagaData data) {
        ResultWithEvents<Order> oe = Order.createOrder(data.getOrderDto());
        Order order = oe.result;
        orderRepository.save(order);
        data.setOrderId(order.getId());
    }

    private CommandWithDestination reserveProduct(CreateOrderSagaData data) {
        long orderId = data.getOrderId();
//        Long customerId = data.getOrderDetails().getCustomerId();
        OrderDto orderDetails = data.getOrderDto();
        return send(new ReserveProductCommand(orderId, orderDetails))
                .to("productService")
                .build();
    }

    private void approve(CreateOrderSagaData data) {
        Optional<Order> order = orderRepository.findById(data.getOrderId());
        order.ifPresent(o -> {
            o.setState(OrderState.APPROVED);
            orderRepository.save(o);
        });
//        orderRepository.findById(data.getOrderId()).get().noteProductReserved();
    }

    public void reject(CreateOrderSagaData data) {
        Optional<Order> order = orderRepository.findById(data.getOrderId());
        order.ifPresent(o -> {
            o.setState(OrderState.REJECTED);
            o.setRejectionReason(data.getRejectionReason());
            orderRepository.save(o);
        });
    }

    private void handleOutOfStock(CreateOrderSagaData data, ProductOutOfStock reply) {
        data.setRejectionReason(RejectionReason.OUT_OF_STOCK);
        reject(data);
    }

}
