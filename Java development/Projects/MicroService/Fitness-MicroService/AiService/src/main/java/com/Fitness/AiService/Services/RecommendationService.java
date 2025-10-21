package com.Fitness.AiService.Services;

import com.Fitness.AiService.Models.Recommendation;
import com.Fitness.AiService.Repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepo recommendationRepo;


    public List<Recommendation> getUserRecommendation(Long userId) {
        return recommendationRepo.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId){
        return recommendationRepo.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No Recommendation Found For This Activity : " + activityId));
    }
}
