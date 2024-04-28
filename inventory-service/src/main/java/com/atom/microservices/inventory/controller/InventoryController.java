package com.atom.microservices.inventory.controller;

import com.atom.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

        private final InventoryService inventoryService;

        @GetMapping
        @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
            return inventoryService.isInStock(skuCode, quantity);
        }

        @PutMapping
        @ResponseStatus(HttpStatus.OK)
    public void updateQuantity(@RequestParam String skuCode, @RequestParam Integer quantity) {
            inventoryService.updateQuantity(skuCode, quantity);
        }

}
