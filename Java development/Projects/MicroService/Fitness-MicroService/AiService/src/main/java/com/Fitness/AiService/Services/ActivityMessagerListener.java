package com.Fitness.AiService.Services;

import com.Fitness.AiService.Models.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessagerListener {


    @KafkaListener(topics = "${kafka.topic.name}" , groupId = "activity-processor-group")
    public void processActivity(Activity activity){
        log.info("Received Activity For Processing : {}", activity.getUserId());
    }
}
