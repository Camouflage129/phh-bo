package com.example.phh.bo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/change")
public class ProbeController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    private String k8s_key = "phh-bo";
    public ProbeController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/readiness/accepting")
    public String markReadinesAcceptingTraffic(@RequestHeader(value="x-k8s-key") String key) {
        if(key.equals(k8s_key)) {
            AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC);
            return "ACCEPTING_TRAFFIC";
        } else {
            return "Non Authentication";
        }
    }

    @GetMapping("/readiness/refuse")
    public String markReadinesRefusingTraffic(@RequestHeader(value="x-k8s-key") String key) {
        if(key.equals(k8s_key)) {
            AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.REFUSING_TRAFFIC);
            return "REFUSING_TRAFFIC";
        } else {
            return "Non Authentication";
        }
    }

    @GetMapping("/liveness/correct")
    public String markLivenessCorrect(@RequestHeader(value="x-k8s-key") String key) {
        if(key.equals(k8s_key)) {
            AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.CORRECT);
            return "CORRECT";
        } else {
            return "Non Authentication";
        }
    }


    @GetMapping("/liveness/broken")
    public String markLivenessBroken(@RequestHeader(value="x-k8s-key") String key) {
        if(key.equals(k8s_key)) {
            AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.BROKEN);
            return "BROKEN";
        } else {
            return "Non Authentication";
        }
    }
}