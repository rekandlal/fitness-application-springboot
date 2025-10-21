package com.Fitness.AiService.Repository;

import com.Fitness.AiService.Models.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepo extends MongoRepository<Recommendation ,String> {


    List<Recommendation> findByUserId(Long userId);


    Optional<Recommendation> findByActivityId(String activityId);


}
