package com.kouz.microservices.core.composite.product.services;

import static org.springframework.http.HttpMethod.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kouz.microservices.api.core.product.Product;
import com.kouz.microservices.api.core.product.ProductService;
import com.kouz.microservices.api.core.recommendation.Recommendation;
import com.kouz.microservices.api.core.recommendation.RecommendationService;
import com.kouz.microservices.api.core.review.Review;
import com.kouz.microservices.api.core.review.ReviewService;
import com.kouz.util.exceptions.InvalidInputException;
import com.kouz.util.exceptions.NotFoundException;
import com.kouz.util.http.HttpErrorInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductCompositeIntegration
        implements ProductService, RecommendationService, ReviewService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,

            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") int productServicePort,

            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") int recommendationServicePort,

            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") int reviewServicePort
    ) {

        this.restTemplate = restTemplate;
        this.mapper = mapper;

        productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product/";
        recommendationServiceUrl =
                "http://" + recommendationServiceHost + ":" + recommendationServicePort
                        + "/recommendation?productId=";
        reviewServiceUrl =
                "http://" + reviewServiceHost + ":" + reviewServicePort + "/review?productId=";
    }

    @Override
    public Product getProduct(int productId) {

        try {
            String url = productServiceUrl + productId;
            log.debug("Will call getProduct API on URL: {}", url);

            Product product = restTemplate.getForObject(url, Product.class);
            log.debug("Found a product with id: {}", product.getProductId());

            return product;
        } catch (HttpClientErrorException exception) {

            switch (exception.getStatusCode()) {

                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(exception));

                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(getErrorMessage(exception));

                default:
                    log.warn("Got a unexpected HTTP error: {}, will rethrow it",
                            exception.getStatusCode());
                    log.warn("Error body: {}", exception.getResponseBodyAsString());
                    throw exception;
            }
        }
    }

    private String getErrorMessage(HttpClientErrorException clientErrorException) {
        try {
            return mapper.readValue(clientErrorException.getResponseBodyAsString(),
                    HttpErrorInfo.class)
                    .getMessage();
        } catch (IOException ioException) {
            return ioException.getMessage();
        }
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {

        try {
            String url = recommendationServiceUrl + productId;

            log.debug("Will call getRecommendations API on URL: {}", url);
            List<Recommendation> recommendations = restTemplate.exchange(
                    url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {
                    })
                    .getBody();

            log.debug("Found {} recommendations for a product with id: {}",
                    recommendations.size(),
                    productId);
            return recommendations;
        } catch (Exception exception) {
            log.warn(
                    "Got an exception while requesting recommendations, return zero recommendations: {}",
                    exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Review> getReviews(int productId) {

        try {
            String url = reviewServiceUrl + productId;

            log.debug("Will call getReviews API on URL: {}", url);
            List<Review> reviews = restTemplate.exchange(url, GET, null,
                    new ParameterizedTypeReference<List<Review>>() {
                    })
                    .getBody();

            log.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
            return reviews;
        } catch (Exception exception) {
            log.warn("Got an exception while requesting reviews, return zero reviews: {}",
                    exception.getMessage());
            return new ArrayList<>();
        }
    }
}