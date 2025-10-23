package com.linktic.challenge.inventory.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${app.services.product-service.url}")
public interface ProductServiceClient {
    @GetMapping("/api/v1/products/{id}")
    ProductResponse getProductById(@PathVariable("id") String productId);
}