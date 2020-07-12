package com.example.order.web;

import com.example.domain.OrderDto;
import com.example.order.domain.OrderService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private OrderController orderController;

    @Autowired
    protected WebApplicationContext wac;


    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(orderController)
                .build();
    }

    @Test
    public void testGetOrder() throws Exception {
        long orderId = 1l;
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);

        BDDMockito.given(orderService.getOrder(orderId)).willReturn(Optional.of(orderDto));


        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId", Matchers.is(1)));
        Mockito.verify(orderService, Mockito.times(1)).getOrder(orderId);
    }
}
