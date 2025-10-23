package com.linktic.challenge.inventory.infrastructure.persistence.entity;

import jakarta.persistence.Entity;

import com.linktic.challenge.inventory.domain.model.Inventory;
import com.linktic.challenge.inventory.domain.model.InventoryId;
import com.linktic.challenge.inventory.domain.model.StockQuantity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntity {

    @Id
    @Column(name = "product_id", length = 50, nullable = false, unique = true)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}