package com.Fitness.ActivityService.Controller;

import com.Fitness.ActivityService.DTO.activityRequest;
import com.Fitness.ActivityService.DTO.activityResponse;
import com.Fitness.ActivityService.Services.activityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/activities")
public class ActivityController {

    private activityService activityService;

    @PostMapping("/add")
    public ResponseEntity<activityResponse> trackActivity(@RequestBody activityRequest request){
        return ResponseEntity.ok(activityService.trackActivity(request));
    }
}
