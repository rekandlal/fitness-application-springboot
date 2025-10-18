package com.Fitness.ActivityService.Services;

import com.Fitness.ActivityService.DTO.activityRequest;
import com.Fitness.ActivityService.DTO.activityResponse;
import com.Fitness.ActivityService.Model.Activity;
import com.Fitness.ActivityService.Repository.activityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class activityService {

    private final activityRepository activityRepository;
    private final UserValidationService userValidationService;


    public activityResponse trackActivity(activityRequest request){

        Boolean isValidUser = userValidationService.validateUser(request.getUserId());

        if (!isValidUser){
            throw new RuntimeException("Invalid User : "+request.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return mapToResponse(savedActivity);

    }

    private activityResponse mapToResponse(Activity savedActivity) {

        activityResponse activityResponse = new activityResponse();
        activityResponse.setId(savedActivity.getId());
        activityResponse.setUserId(savedActivity.getUserId());
        activityResponse.setType(savedActivity.getType());
        activityResponse.setDuration(savedActivity.getDuration());
        activityResponse.setCaloriesBurned(savedActivity.getCaloriesBurned());
        activityResponse.setStartTime(savedActivity.getStartTime());
        activityResponse.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        activityResponse.setCreatedAt(savedActivity.getCreatedAt());
        activityResponse.setUpdateAt(savedActivity.getUpdateAt());

        return activityResponse;
    }
}
