package com.ebenzlab.catalogservice.controllers;

import com.ebenzlab.catalogservice.entities.Product;
import com.ebenzlab.catalogservice.exceptions.ProductNotFoundException;
import com.ebenzlab.catalogservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Slf4j
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> allProduct(HttpServletRequest request){
        log.info("Finding all product");
        String authHeader = request.getHeader("AUTH_HEADER");
        log.info("AUTH_HEADER"+ authHeader);
        return  productService.findAllProducs();
    }

    @GetMapping
    public Product productByCode(@PathVariable String code){
        log.info("Finding product by codde" + code);
        return productService.findProductByCode(code).orElseThrow(() -> new ProductNotFoundException("Product with code ["+code+"] doesn't exist"));
    }
}
