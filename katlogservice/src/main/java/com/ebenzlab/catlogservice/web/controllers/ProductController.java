package com.ebenzlab.catlogservice.web.controllers;

import com.ebenzlab.catlogservice.entities.Product;
import com.ebenzlab.catlogservice.exceptions.ProductNotFoundException;
import com.ebenzlab.catlogservice.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> allProduct(HttpServletRequest request){
        log.info("Finding all product");
        String authHeader = request.getHeader("AUTH_HEADER");
        System.out.println("Auth_Header" + authHeader);
        log.info("AUTH_HEADER"+ authHeader);
        return  productService.findAllProducs();
    }

    @GetMapping("/{code}")
    public Product productByCode(@PathVariable String code){
        log.info("Finding product by code" + code);
        return productService.findProductByCode(code).orElseThrow(() -> new ProductNotFoundException("Product with code ["+code+"] doesn't exist"));
    }
}
