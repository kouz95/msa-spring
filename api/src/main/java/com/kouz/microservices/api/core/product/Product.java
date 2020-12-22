package com.kouz.microservices.api.core.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Product {
    private final int productId;
    private final String name;
    private final int weight;
    private final String serviceAddress;

    private Product() {
        productId = 0;
        name = null;
        weight = 0;
        serviceAddress = null;
    }
}
