package com.ebenzlab.catalogservice.services;

import com.ebenzlab.catalogservice.web.models.ProductInventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "Inventory-Client")
public interface InventoryServiceFeignClient {

    @GetMapping("api/inventory")
    List<ProductInventoryResponse> getInventoryLevels();

    @GetMapping("api/inventory/{productCode}")
    List<ProductInventoryResponse> getInventoryProductCode(String code);
}
