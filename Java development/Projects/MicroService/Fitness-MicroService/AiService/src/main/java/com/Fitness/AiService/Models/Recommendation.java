package com.Fitness.AiService.Models;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "AiRecommendationFitness")
@Data
@Builder
public class Recommendation {

    @Id
    private String id;
    private Long userId;
    private String type;
    private String activityId;
    private String recommendation; // full analysis data store here

    private List<String> improvements; // store improvement data
    private List<String> suggestions; // store suggestions data
    private List<String> safety;// store safety data

    @CreatedDate
    private LocalDateTime createdAt;

}
