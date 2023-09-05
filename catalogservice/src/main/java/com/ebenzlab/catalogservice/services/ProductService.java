package com.ebenzlab.catalogservice.services;

import com.ebenzlab.catalogservice.entities.Product;
import com.ebenzlab.catalogservice.repository.ProductRepository;
import com.ebenzlab.catalogservice.web.models.ProductInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final InventoryServiceClient inventoryServiceClient;

    public ProductService(ProductRepository productRepository, InventoryServiceClient inventoryServiceClient) {
        this.productRepository = productRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public List<Product> findAllProducs(){
    List<Product> products = productRepository.findAll();
    final Map<String, Integer> inventoryLevels = getInventoryLevelsWithFeignClients();
    final List<Product> availableProducts = products.stream()
            .filter(p -> inventoryLevels.get(p.getCode()) != null && inventoryLevels.get(p.getCode()) > 0)
            .collect(Collectors.toList());
    return availableProducts;
    }

    private Map<String, Integer> getInventoryLevelsWithFeignClients() {

        Map<String, Integer> inventoryLevels = new HashMap<>();
        List<ProductInventoryResponse> inventory = inventoryServiceClient.getProductInventoryLevels();
        for (ProductInventoryResponse item: inventory){
            inventoryLevels.put(item.getProductCode(), item.getAvailableQuantity())
        }
        log.debug("inventoryLevels: {}" + inventoryLevels);
        return inventoryLevels;
    }
}
