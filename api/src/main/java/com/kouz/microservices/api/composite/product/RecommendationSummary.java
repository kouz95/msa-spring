package com.kouz.microservices.api.composite.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RecommendationSummary {
    private final int recommendationId;
    private final String author;
    private final int rate;
    private final String content;

    protected RecommendationSummary() {
        this.recommendationId = 0;
        this.author = null;
        this.rate = 0;
        this.content = null;
    }
}
