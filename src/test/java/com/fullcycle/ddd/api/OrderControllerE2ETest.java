package com.fullcycle.ddd.api;

import com.fullcycle.ddd.application.usecase.PlaceOrderHandler;
import com.fullcycle.ddd.application.usecase.PayOrderHandler;
import com.fullcycle.ddd.application.usecase.CancelOrderHandler;
import com.fullcycle.ddd.domain.valueobject.OrderId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceOrderHandler placeOrder;

    @MockBean
    private PayOrderHandler payOrder;

    @MockBean
    private CancelOrderHandler cancelOrder;

    @Test
    void createOrderE2E() throws Exception {
        when(placeOrder.handle(any())).thenReturn(new OrderId("o1"));

        mockMvc.perform(post("/orders")
                        .contentType("application/json")
                        .content("{\"eventId\":\"e1\",\"customerId\":\"c1\",\"seats\":[\"s1\"],\"total\":100}"))
                .andExpect(status().isOk());

        verify(placeOrder, times(1)).handle(any());
    }

    @Test
    void payOrderE2E() throws Exception {
        mockMvc.perform(post("/orders/o1/pay"))
                .andExpect(status().isOk());

        verify(payOrder, times(1)).handle(any());
    }

    @Test
    void cancelOrderE2E() throws Exception {
        mockMvc.perform(post("/orders/o1/cancel")
                        .contentType("application/json")
                        .content("{\"reason\":\"cliente desistiu\"}"))
                .andExpect(status().isOk());

        verify(cancelOrder, times(1)).handle(any());
    }
}