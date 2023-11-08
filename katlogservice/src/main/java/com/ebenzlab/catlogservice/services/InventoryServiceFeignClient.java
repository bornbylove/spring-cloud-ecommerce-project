package com.ebenzlab.catlogservice.services;


import com.ebenzlab.catlogservice.web.models.ProductInventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "Inventory-service", url = "http://localhost:8282")
public interface InventoryServiceFeignClient {

    @GetMapping("api/inventory")
    List<ProductInventoryResponse> getInventoryLevels();

    @GetMapping("api/inventory/{productCode}")
    List<ProductInventoryResponse> getInventoryProductCode(String code);
}
