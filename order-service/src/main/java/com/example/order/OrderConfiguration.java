package com.example.order;

import com.example.order.domain.OrderRepository;
import com.example.order.domain.OrderService;
import com.example.order.domain.OrderServiceImpl;
import com.example.sagas.createorder.CreateOrderSaga;
import io.eventuate.tram.sagas.orchestration.*;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import({SagaOrchestratorConfiguration.class, OptimisticLockingDecoratorConfiguration.class})
public class OrderConfiguration {

    @Bean
    public OrderService orderService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
        return new OrderServiceImpl(orderRepository, sagaInstanceFactory, createOrderSaga);
    }

    @Bean
    public CreateOrderSaga createOrderSaga(OrderRepository orderRepository) {
        return new CreateOrderSaga(orderRepository);
    }

}
