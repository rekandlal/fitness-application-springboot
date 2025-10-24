package com.Fitness.AiService.Services;

import com.Fitness.AiService.Models.Activity;
import com.Fitness.AiService.Models.Recommendation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity){
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getRecommendations(prompt);
        // input me hum text ke andhar prompt denge
        log.info("RESPONSE FROM AI {} ", aiResponse);

        return processAiResponse(activity , aiResponse);
        
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {

        try{
            ObjectMapper mapper =  new ObjectMapper();
            // rootNode: hum abhi candidates me route hai


            //{
            //    "candidates": [
            //        {
            //            "content": {
            //                "parts": [
            //                    {
            //                        "text": "AI learns from data to make smart decisions."
            //                    }
            //                ],
            //                "role": "model"
            //            },
            //            "finishReason": "STOP",
            //            "index": 0
            //        }
            //    ],
            //    "usageMetadata": {
            //        "promptTokenCount": 8,
            //        "candidatesTokenCount": 9,
            //        "totalTokenCount": 954,
            //        "promptTokensDetails": [
            //            {
            //                "modality": "TEXT",
            //                "tokenCount": 8
            //            }
            //        ],
            //        "thoughtsTokenCount": 937
            //    },
            //    "modelVersion": "gemini-2.5-flash",
            //    "responseId": "jYf4aPOvDpqkvr0Pgu-yoAM"
            //}
            JsonNode rootNode = mapper.readTree(aiResponse); // Ai based response aa raha hai wo json format me hoga and wo json me store rakhega
            // then we extract inner data easily.

            // textNode : abhi hum textNode pe route hai
            //"text": "AI learns from data to make smart decisions."
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");

            // perfect text format me data lana
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("\\n```","")
                    .replaceAll("\\n```","").trim();

//            log.info("RESPONSE FROM CLEANED AI {} ", jsonContent);

            JsonNode analysisJson = mapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");


            //"analysis": {
            //        "overall": "This 20-minute cycling activity presents highly contradictory data. While an impressive 15.5 km was covered at an average speed of 46.5 km/h, leading to a significant calorie burn of 310, the reported average heart rate of 120 bpm is unusually low for such a demanding performance. This suggests either an assisted ride (e.g., e-bike), highly inaccurate sensor readings, or an exceptionally high level of fitness combined with extreme efficiency. Furthermore, the 'steps' metric (40050) is irrelevant and highly unusual for a cycling activity, strongly indicating a data tracking error or misclassification.",
            //        "pace": "The calculated average speed is an exceptionally high 46.5 km/h (15.5 km in 20 minutes). For an unassisted ride, this is a professional-level pace for a sustained effort. If accurate, it demonstrates outstanding power and speed. However, this pace severely conflicts with the reported average heart rate of 120 bpm, which typically corresponds to a moderate effort. It is crucial to verify the accuracy of the distance and speed tracking, as this discrepancy suggests possible GPS errors, an assisted ride, or a significantly downhill route.",
            //        "heartRate": "An average heart rate of 120 bpm for a 20-minute activity generally falls within a moderate intensity zone (Zone 2-3, approximately 60-75% of maximum heart rate for many individuals). While this is a healthy zone for cardiovascular conditioning, it is surprisingly low for the extremely high reported speed of 46.5 km/h and the substantial calorie burn of 310 kcal. This significant inconsistency suggests that either the heart rate monitor reading is inaccurate, or the perceived intensity of the effort was not as high as implied by the speed and calorie metrics.",
            //        "caloriesBurned": "Burning 310 calories in just 20 minutes translates to an average burn rate of 15.5 calories per minute. This is an exceptionally high rate, typically achieved during very vigorous activities or by individuals with higher body mass. This high calorie expenditure is consistent with the reported high speed (46.5 km/h) but directly conflicts with the moderate average heart rate of 120 bpm. This further highlights the need for data validation, as such a high calorie burn rate usually correlates with a much higher heart rate for unassisted cycling."
            //    },
            //    "improvements": [
            //        {
            //            "area": "Data Accuracy and Consistency",
            //            "recommendation": "Given the significant inconsistencies (high speed/calorie burn vs. moderate HR, and extraneous 'steps' data), it is crucial to verify the accuracy of your fitness tracker's sensors (GPS, heart rate, power meter if applicable). Ensure devices are properly calibrated, worn correctly, and that activity types are accurately recorded. If using an e-bike or riding with significant external assistance (e.g., strong tailwind, long downhill), consider noting that in your activity logs for more accurate performance interpretation. Understanding the true effort is fundamental to effective training."
            //        },
            //        {
            //            "area": "Sustained Moderate-High Intensity (Aerobic Base)",
            //            "recommendation": "If the heart rate of 120 bpm is accurate and represents your moderate effort zone, focus on extending the duration of your rides within this zone (Zone 2-3, 60-75% of max HR) to build aerobic endurance. Aim for 45-60 minutes at a consistent moderate effort, which will improve your cardiovascular system's efficiency and ability to sustain effort over time, making longer rides feel easier without excessive fatigue."
            //        },
            //        {
            //            "area": "Cadence and Cycling Efficiency",
            //            "recommendation": "For efficient cycling, maintaining an optimal cadence (revolutions per minute, typically 80-100 RPM for road cycling) is crucial. If you have a cadence sensor, monitor and try to maintain a smooth, consistent pedal stroke. This helps optimize power output, reduces muscle fatigue, and allows you to sustain efforts for longer periods. If the reported speed is genuinely high, maintaining a high and consistent cadence is essential for minimizing effort and maximizing performance."
            //        },
            //        {
            //            "area": "Targeted Intensity Training (if high speed is a goal)",
            //            "recommendation": "If the goal is to develop the ability to sustain extremely high speeds (like the 46.5 km/h implied by the distance), incorporate structured interval training. This involves alternating short bursts of very high intensity (e.g., 90-100% of maximum effort, pushing HR into Zone 4-5) with periods of lower intensity recovery. This type of training is highly effective for improving speed, power, and anaerobic capacity, which are necessary for such high velocities."
            //        }
            //    ],
            //    "suggestions": [
            //        {
            //            "workout": "Endurance Base Ride (Zone 2-3)",
            //            "description": "Aim for a 45-60 minute ride at a consistent moderate pace, keeping your heart rate in your Zone 2-3 (approximately 60-75% of your estimated maximum heart rate). Focus on smooth pedaling and maintaining a steady effort throughout. This workout is excellent for building your aerobic base, improving cardiovascular efficiency, and increasing your stamina for longer rides."
            //        },
            //        {
            //            "workout": "High-Intensity Interval Training (HIIT) on Bike",
            //            "description": "After a 10-minute dynamic warm-up, perform 4-6 intervals of 1-minute all-out effort (maximal sustainable pace, aiming for HR Zone 4-5) followed by 2-3 minutes of easy recovery cycling. Conclude with a 10-minute cool-down. This workout significantly boosts your speed, power, and anaerobic threshold, which are crucial for achieving and maintaining high velocities."
            //        },
            //        {
            //            "workout": "Hill Repeats or Resistance Power Training (Indoor)",
            //            "description": "Find a moderate hill that takes 2-4 minutes to climb, or if indoors, increase resistance on your trainer. After a warm-up, climb the hill (or apply resistance) at a hard but sustainable effort (HR Zone 3-4, strong leg engagement), then descend (or reduce resistance) for active recovery. Repeat 4-6 times. This workout builds leg strength, power, and muscular endurance, which are vital for maintaining high speeds and tackling varied terrain."
            //        }
            //    ],
            //    "safety": [
            //        "Always wear a properly fitted helmet. It is the most critical piece of safety equipment.",
            //        "Follow traffic laws, use appropriate hand signals, and be acutely aware of your surroundings (vehicles, pedestrians, road conditions, debris).",
            //        "Ensure your bicycle is in excellent working condition: check tire pressure, brake functionality, chain lubrication, and quick-release levers before each ride.",
            //        "Stay hydrated by drinking water or an electrolyte solution before, during, and after your ride, especially for longer durations or in warm weather.",
            //        "If cycling outdoors, use front and rear lights, even during daylight hours, and wear bright, reflective clothing to enhance your visibility to others.",
            //        "Listen to your body. If you experience unusual pain, dizziness, or excessive fatigue, reduce intensity or stop the activity. Consult a medical professional if symptoms persist.",
            //        "Inform someone of your planned route and estimated return time, especially for solo rides or rides in less populated areas."
            //    ]
            //}


            // we convert readable format
            StringBuilder fullAnalysis = new StringBuilder();

            //===== for analysis =======
            // for overall content
            addAnalysisSection(fullAnalysis ,analysisNode, "overall" , "OverAll : " );

            // for pace content
            addAnalysisSection(fullAnalysis ,analysisNode, "pace" , "Pace : " );

            // for hearRate content
            addAnalysisSection(fullAnalysis ,analysisNode, "heartRate" , "Heart Rate : " );

            // for caloriesBurned content
            addAnalysisSection(fullAnalysis ,analysisNode, "caloriesBurned" , "Calories Burned : " );

            // ======= for improvements ============
            // extract all keys :
            // why we use list have multiple keys
            List<String> improvements = extractImprovements(analysisJson.path("improvements"));


            // ======= for suggestions ============
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));


            // ======= for safety ============
            List<String> safety = extractSafety(analysisJson.path("safety"));

            // Recommendation recommendation = new Recommendation();
            //recommendation.setActivityId(activity.getId());
            //recommendation.setUserId(activity.getUserId());
            //recommendation.setRecommendation(fullAnalysis.toString());
            //recommendation.setImprovements(improvements);
            //recommendation.setSuggestions(suggestions);
            //recommendation.setSafety(safety);
            //
            //return recommendation;

            // this is another to create object and assign value
            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType().toString())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

            
        }catch (Exception e){
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }

    }

    // Create Default Recommendation
    private Recommendation createDefaultRecommendation(Activity activity) {

        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType().toString())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider consulting a fitness consultant"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }


    // for analysis method
    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
            if (!analysisNode.path(key).isMissingNode()){
                fullAnalysis.append(prefix)
                        .append(analysisNode.path(key).asText())
                        .append("\n\n");
            }
    }

    // for improvement method
    private List<String> extractImprovements(JsonNode improvementNode) {
        List<String> improvementsResponse = new ArrayList<>();

        if(improvementNode.isArray()){
            improvementNode.forEach( improvement -> {
                String area = improvement.path("area").asText();
                String recommendation = improvement.path("recommendation").asText();

                improvementsResponse.add(String.format("%s: %s",area,recommendation));
            });
        }

        return improvementsResponse.isEmpty() ? Collections.singletonList("No Specific Improvements Provided"): improvementsResponse;
    }

    // for suggestion method
    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestionResponse = new ArrayList<>();

        if(suggestionsNode.isArray()){
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();

                suggestionResponse.add(String.format("%s : %s",workout,description));

            });

        }

        return suggestionResponse.isEmpty() ? Collections.singletonList("No Specific Suggestion Provided") : suggestionResponse;
    }

    // for safety method
    private List<String> extractSafety(JsonNode safetyNode) {
        List<String> safetyResponse = new ArrayList<>();

        if(safetyNode.isArray()){
            safetyNode.forEach(item -> safetyResponse.add(item.asText()));
        }

        return safetyResponse.isEmpty() ? Collections.singletonList("Follow Generic Safety Guidelines") : safetyResponse;
    }



    // create best prompt for activity
    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendation in the following EXACT JSON format:
        {
            "analysis": {
                      "overall": "Overall analysis here",
                      "pace": "Pace analysis here",
                      "heartRate": "Heart rate analysis here",
                      "caloriesBurned": "Calories analysis here"
                      },
            "improvements": [
                  {
                  "area": "Area name",
                          "recommendation": "Detailed recommendation"
                  }
                            ],
        
            "suggestions":[
                  {
                  "workout": "Workout name",
                  "description": "Detailed workout description"
                  }
                            ],
       
            "safety": [
                          "Safety point 1",
                          "Safety point 2"
                       ]
        
        }
        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """ ,
                    activity.getType(),
                    activity.getDuration(),
                    activity.getCaloriesBurned(),
                    activity.getAdditionalMetrics());
    }
}
