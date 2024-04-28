package com.atom.microservices.inventory.service;

import com.atom.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        // Find the inventory by skuCode where quantity is greater than or equal to the requested quantity
       return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    public void updateQuantity(String skuCode, Integer quantity) {
        // Deduct the requested quantity from the inventory
        inventoryRepository.updateQuantity(skuCode, quantity);
    }
}
