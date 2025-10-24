package com.Fitness.AiService.Services;

import com.Fitness.AiService.Models.Activity;
import com.Fitness.AiService.Models.Recommendation;
import com.Fitness.AiService.Repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessagerListener {

    private final ActivityAiService activityAiService;
    private final RecommendationRepo recommendationRepo;

    @KafkaListener(topics = "${kafka.topic.name}" , groupId = "activity-processor-group")
    public void processActivity(Activity activity){
        log.info("Received Activity For Processing : {}", activity.getUserId());

        try {
            Recommendation recommendation = activityAiService.generateRecommendation(activity);
            recommendationRepo.save(recommendation);
            log.info("Saved Recommendation for userId: {}", activity.getUserId());
        } catch (Exception e) {
            log.error("Error saving recommendation for userId: {}", activity.getUserId(), e);
        }
    }

}
