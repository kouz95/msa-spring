package com.kouz.microservices.api.core.recommendation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Recommendation {
    private final int productId;
    private final int recommendationId;
    private final String author;
    private final int rate;
    private final String content;
    private final String serviceAddress;
}
