package com.example.order.domain;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    protected OrderRepository orderRepository;

    @Test
    public void repositoryNotNull() {
        Assert.assertNotNull("orderRepository is null", orderRepository);
    }

    @Test
    @Transactional
    public void testSaveOrder() {

        Order order = new Order();
        order.setCustomerId(1l);
        order.setState(OrderState.PENDING);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setPrice(new BigDecimal(1));
        orderDetails.setProductId(1l);
        orderDetails.setAmount(1l);
        List<OrderDetails> details = new ArrayList<>();
        details.add(orderDetails);
        order.setOrderDetails(details);

        orderRepository.save(order);

        Assert.assertNotNull("Order id must not be null", order.getId());

        Optional<Order> orderFromDb = orderRepository.findById(order.getId());
        Assert.assertTrue("Order id must found in database", orderFromDb.isPresent());
        Assert.assertThat("Order Details is empty", 1, Matchers.is(orderFromDb.get().getOrderDetails().size()));
    }
}
