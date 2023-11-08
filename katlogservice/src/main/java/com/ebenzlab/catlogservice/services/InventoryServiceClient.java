package com.ebenzlab.catlogservice.services;

import com.ebenzlab.catlogservice.web.models.ProductInventoryResponse;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class InventoryServiceClient {
    @Autowired
    InventoryServiceFeignClient inventoryServiceFeignClient;
    @Autowired
    private final RestTemplate restTemplate;
    private static final String INVENTORY_API_PATH = "http://inventory-service/api/";

    public InventoryServiceClient(RestTemplate restTemplate, InventoryServiceFeignClient inventoryServiceFeignClient) {
        this.restTemplate = restTemplate;
        this.inventoryServiceFeignClient = inventoryServiceFeignClient;
    }

    /*@HystrixCommand(fallbackMethod = "getDefaultProductInventoryLevels",
            commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutMilliseconds", value = "1000")
            }
    )*/
    public List<ProductInventoryResponse> getProductInventoryLevels(){
        return this.inventoryServiceFeignClient.getInventoryLevels();
    }

   /* List<ProductInventoryResponse> getDefaultInventoryLevels(){
        log.info("Returning default inventory list");
        return new ArrayList<>();
    }*/

   /* @HystrixCommand(fallbackMethod = "GetDefaultProductInventoryByCode")
   * */
    public Optional<ProductInventoryResponse> getProductInventoryByCode(String productCode){
        ResponseEntity<ProductInventoryResponse> itemResponseEntity =
                restTemplate.getForEntity(INVENTORY_API_PATH + "inventory/{code}",
                        ProductInventoryResponse.class, productCode);
        if (itemResponseEntity.getStatusCode() == HttpStatus.OK){
            Integer quantity = itemResponseEntity.getBody().getAvailableQuantity();
            log.info("Available quantity:" + quantity);
            return Optional.ofNullable(itemResponseEntity.getBody());
        }else {
            log.info("Unable to get inventory level for productCode:" + productCode + "StatusCode:" + itemResponseEntity.getStatusCode());
            return Optional.empty();
        }
    }

    /*Optional<ProductInventoryResponse> getDefaultProductInventoryByCode(String productCode){
        log.info("Return default productInventoryByCode for productCode:" + productCode);
       // log.info("correlationId" + myThreadLocalHolder.getCorrelationId());
        ProductInventoryResponse response = new ProductInventoryResponse();
        response.setProductCode(productCode);
        response.setAvailableQuantity(50);
        return Optional.ofNullable(response);
    }*/
}
