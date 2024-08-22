package org.example.judge1master.controller;

import constants.KafkaConstants;
import org.example.common.entity.SubmissionRequest;
import org.example.common.entity.SubmissionResponse;
import org.example.judge1master.repository.SubmissionRequestRepository;
import org.example.judge1master.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submit")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Autowired
    SubmissionRequestRepository submissionRequestRepository;

    @PostMapping
    public ResponseEntity<?> submit(@RequestBody SubmissionRequest submissionRequest) {
        submissionRequest = submissionRequestRepository.save(submissionRequest);
        submissionService.sendSubmission(submissionRequest);
        return ResponseEntity.ok().body(submissionRequest.get_id());
    }

    @PostMapping("/{submissionId}")
    public ResponseEntity<Object> getSubmissionResult(@PathVariable String submissionId) {
        Object result = submissionService.getSubmissionResult(submissionId);
        return ResponseEntity.ok(result);
    }
}
