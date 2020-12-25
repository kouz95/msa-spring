package com.kouz.microservices.core.recommendation.recommendation.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends CrudRepository<RecommendationEntity, String> {
    List<RecommendationEntity> findByProductId(int productId);
}
