package com.ebenzlab.catlogservice.web.models;

import lombok.Data;

@Data
public class ProductInventoryResponse {
private String productCode;
private Integer availableQuantity;
}
