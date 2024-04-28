package com.atom.microservices.order.service;

import com.atom.microservices.order.client.InventoryClient;
import com.atom.microservices.order.dto.OrderRequest;
import com.atom.microservices.order.model.Order;
import com.atom.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        // Check if the requested quantity is available in the inventory
        if (!inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity())) {
            log.error("Requested quantity is not available in the inventory for skuCode: {}", orderRequest.skuCode());
            throw new RuntimeException("Requested quantity is not available in the inventory " + orderRequest.skuCode());
        }
        // Map orderRequest to Order entity
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(orderRequest.skuCode())
                .price(orderRequest.price())
                .quantity(orderRequest.quantity())
                .build();
        // Save the Order entity to the database
        orderRepository.save(order);
        // Deduct the requested quantity from the inventory
        inventoryClient.updateQuantity(orderRequest.skuCode(), orderRequest.quantity());
        log.info("Order placed successfully: {}", order);

    }
}
