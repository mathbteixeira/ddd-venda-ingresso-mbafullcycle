package com.fullcycle.ddd.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceOrderHandler placeOrder;

    @MockBean
    private PayOrderHandler payOrder;

    @MockBean
    private CancelOrderHandler cancelOrder;

    @Test
    void testCreateOrder() throws Exception {
        // Mock para retornar um OrderId qualquer
        when(placeOrder.handle(any())).thenReturn(new OrderId("o1"));

        mockMvc.perform(post("/orders")
                        .contentType("application/json")
                        .content("{\"eventId\":\"e1\",\"customerId\":\"c1\",\"seats\":[\"s1\"],\"total\":100}"))
                .andExpect(status().isOk());

        verify(placeOrder, times(1)).handle(any());
    }

    @Test
    void testPayOrder() throws Exception {
        mockMvc.perform(post("/orders/o1/pay"))
                .andExpect(status().isOk());

        verify(payOrder, times(1)).handle(any());
    }

    @Test
    void testCancelOrder() throws Exception {
        mockMvc.perform(post("/orders/o1/cancel")
                        .contentType("application/json")
                        .content("{\"reason\":\"cliente desistiu\"}"))
                .andExpect(status().isOk());

        verify(cancelOrder, times(1)).handle(any());
    }
}