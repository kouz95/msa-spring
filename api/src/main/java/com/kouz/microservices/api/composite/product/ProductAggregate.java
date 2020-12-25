package com.kouz.microservices.api.composite.product;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductAggregate {
    private final int productId;
    private final String name;
    private final int weight;
    private final List<RecommendationSummary> recommendations;
    private final List<ReviewSummary> reviews;
    private final ServiceAddresses serviceAddresses;

    protected ProductAggregate() {
        productId = 0;
        name = null;
        weight = 0;
        recommendations = null;
        reviews = null;
        serviceAddresses = null;
    }
}
