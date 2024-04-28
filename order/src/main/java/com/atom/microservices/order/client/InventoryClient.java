package com.atom.microservices.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service" , url = "${inventory.service.url}")
public interface InventoryClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/inventory")
     boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/inventory")
     void updateQuantity(@RequestParam String skuCode, @RequestParam Integer quantity);
}
