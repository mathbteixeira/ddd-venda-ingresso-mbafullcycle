package com.fullcycle.ddd.api;

import com.fullcycle.ddd.application.usecase.*;
import com.fullcycle.ddd.domain.entity.OrderId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final PlaceOrderHandler placeOrderHandler;
    private final PayOrderHandler payOrderHandler;
    private final CancelOrderHandler cancelOrderHandler;

    public OrderController(PlaceOrderHandler placeOrderHandler,
                           PayOrderHandler payOrderHandler,
                           CancelOrderHandler cancelOrderHandler) {
        this.placeOrderHandler = placeOrderHandler;
        this.payOrderHandler = payOrderHandler;
        this.cancelOrderHandler = cancelOrderHandler;
    }

    @PostMapping
    public ResponseEntity<OrderId> createOrder(@RequestBody PlaceOrderCommand command) {
        OrderId orderId = placeOrderHandler.handle(command);
        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Void> payOrder(@PathVariable String orderId) {
        payOrderHandler.handle(new PayOrderCommand(orderId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderId,
                                            @RequestBody CancelOrderCommand command) {
        cancelOrderHandler.handle(command);
        return ResponseEntity.ok().build();
    }
}