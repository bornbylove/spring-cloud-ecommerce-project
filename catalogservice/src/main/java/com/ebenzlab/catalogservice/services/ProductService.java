package com.ebenzlab.catalogservice.services;

import com.ebenzlab.catalogservice.entities.Product;
import com.ebenzlab.catalogservice.repository.ProductRepository;
import com.ebenzlab.catalogservice.utils.MyThreadLocalsHolder;
import com.ebenzlab.catalogservice.web.models.ProductInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
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
            inventoryLevels.put(item.getProductCode(), item.getAvailableQuantity());
        }
        log.debug("inventoryLevels: {}" + inventoryLevels);
        return inventoryLevels;
    }

    public Optional<Product> findProductByCode(String code){
        Optional<Product> productOptional = productRepository.findByCode(code);
        if (productOptional.isPresent()){
            String correlationId = UUID.randomUUID().toString();
            MyThreadLocalsHolder.setCorrelationId(correlationId);
            log.info("Before CorrelationID:" + MyThreadLocalsHolder.getCorrelationId());
            log.info("Fetching Inventory Level for productCode" + code);
            Optional<ProductInventoryResponse> itemResponseEntity =
                    this.inventoryServiceClient.getProductInventoryByCode(code);
            if (itemResponseEntity.isPresent()){
                Integer quantity =itemResponseEntity.get().getAvailableQuantity();
                productOptional.get().setInStock(quantity > 0);
            }
            log.info("After CorrelationID:" + MyThreadLocalsHolder.getCorrelationId());
        }
        return productOptional;
    }
}
