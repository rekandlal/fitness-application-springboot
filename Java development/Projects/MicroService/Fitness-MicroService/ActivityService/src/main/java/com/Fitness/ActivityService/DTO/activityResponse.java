package com.Fitness.ActivityService.DTO;

import com.Fitness.ActivityService.Model.ActivityType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class activityResponse {
    private String id;
    private Long userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    private Map<String , Object> additionalMetrics;

}
