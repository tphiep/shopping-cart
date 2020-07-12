package com.example.order.domain;

import com.example.domain.ItemLine;
import com.example.domain.OrderDto;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @MockBean
    protected OrderRepository orderRepository;

    @Autowired
    protected OrderService orderService;

    @Test
    public void testGetOrder_orderNotFound() {
        long id = 1l;
        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.empty());
        Optional<OrderDto> result = orderService.getOrder(id);
        Assert.assertFalse("Order should not present", result.isPresent());
    }

    @Test
    public void testGetOrder() {
        long id = 1l;
        long customerId = 1l;
        // Prepare
        Order order = new Order();
        order.setState(OrderState.PENDING);
        order.setId(id);
        order.setCustomerId(customerId);
        OrderDetails details = new OrderDetails();
        details.setProductId(1l);
        details.setPrice(new BigDecimal(1));
        order.getOrderDetails().add(details);
        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        // Execute
        Optional<OrderDto> result = orderService.getOrder(id);
        // Validate
        Assert.assertTrue("An Order should be returned", result.isPresent());

        validateReturnOrder(result.get(), order);

    }

    private static void validateReturnOrder(OrderDto orderDto, Order order) {
        Assert.assertThat("Order Id is not equal", orderDto.getOrderId(), Matchers.is(order.getId()));
        Assert.assertThat("Customer Id is not equal", orderDto.getCustomerId(), Matchers.is(order.getCustomerId()));
        Assert.assertThat(order.getRejectionReason(), Matchers.is(Matchers.nullValue()));
        Assert.assertThat("Order state is not equal", orderDto.getState(), Matchers.is(order.getState().name()));
        Assert.assertThat("Order Details size", orderDto.getItemLines().size(), Matchers.is(order.getOrderDetails().size()));
        validateOrderDetails(order.getOrderDetails(), orderDto.getItemLines());
    }

    private static void validateOrderDetails(List<OrderDetails> details, List<ItemLine> actual) {

    }
}
