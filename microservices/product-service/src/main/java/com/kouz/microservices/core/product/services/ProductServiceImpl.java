package com.kouz.microservices.core.product.services;

import org.springframework.web.bind.annotation.RestController;

import com.kouz.microservices.api.core.product.Product;
import com.kouz.microservices.api.core.product.ProductService;
import com.kouz.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductServiceImpl implements ProductService {
    private final ServiceUtil serviceUtil;

    @Override
    public Product getProduct(int productId) {
        return new Product(productId, "name-" + productId, 123, serviceUtil.getServiceAddress());
    }
}
