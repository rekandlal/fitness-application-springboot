package com.Fitness.ActivityService.Repository;

import com.Fitness.ActivityService.Model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface activityRepository extends MongoRepository<Activity , String> {
}
