package com.atom.microservices.inventory.repository;

import com.atom.microservices.inventory.model.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE t_inventory SET quantity = quantity - :quantity WHERE sku_code = :skuCode")
    void updateQuantity(String skuCode, Integer quantity);
}
