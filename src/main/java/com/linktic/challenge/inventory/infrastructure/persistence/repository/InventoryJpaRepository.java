package com.linktic.challenge.inventory.infrastructure.persistence.repository;

import com.linktic.challenge.inventory.infrastructure.persistence.entity.InventoryEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryJpaRepository extends JpaRepository<InventoryEntity, String> {

    Optional<InventoryEntity> findByProductId(String productId);

    boolean existsByProductId(String productId);

    @Modifying
    @Query("UPDATE InventoryEntity i SET i.quantity = i.quantity - :quantity WHERE i.productId = :productId AND i.quantity >= :quantity")
    int reduceStock(@Param("productId") String productId, @Param("quantity") Integer quantity);

    @Modifying
    @Query("UPDATE InventoryEntity i SET i.quantity = i.quantity + :quantity WHERE i.productId = :productId")
    int increaseStock(@Param("productId") String productId, @Param("quantity") Integer quantity);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM InventoryEntity i WHERE i.productId = :productId")
    Optional<InventoryEntity> findByProductIdWithLock(@Param("productId") String productId);

}