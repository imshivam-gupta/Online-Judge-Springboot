package org.example.judge1master.service;

import constants.KafkaConstants;
import org.example.common.entity.SubmissionRequest;
import org.example.common.entity.SubmissionResponse;
import org.example.judge1master.repository.SubmissionRequestRepository;
import org.example.judge1master.repository.SubmissionResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionResponseRepository submissionResponseRepository;

    @Autowired
    private SubmissionRequestRepository submissionRequestRepository;

    private final KafkaTemplate<String, SubmissionRequest> kafkaTemplate;

    public SubmissionService(KafkaTemplate<String, SubmissionRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSubmission(SubmissionRequest request) {
        System.out.println("Sending submission request to Kafka: " + request);
        kafkaTemplate.send(KafkaConstants.SUBMISSION_QUEUE, request);
    }

    public Object getSubmissionResult(String submissionRequestId) {
        SubmissionRequest submissionRequest = submissionRequestRepository.findById(submissionRequestId).orElse(null);

        if (submissionRequest == null) {
            return "Submission request not found.";
        }

        int totalTestCases = submissionRequest.getTestCases().size();
        long processedTestCasesCount = submissionResponseRepository.countBySubmissionId(submissionRequestId);

        if (processedTestCasesCount == totalTestCases) {
            return submissionResponseRepository.findBySubmissionId(submissionRequestId);
        } else {
            return "Submission is still being processed.";
        }
    }

}
