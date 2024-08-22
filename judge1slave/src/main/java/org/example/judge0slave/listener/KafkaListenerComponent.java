package org.example.judge0slave.listener;

import constants.KafkaConstants;
import org.example.common.entity.SubmissionRequest;
import org.example.judge0slave.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Component
public class KafkaListenerComponent {

    @Autowired
    private WorkerService workerService;

    @KafkaListener(topics = KafkaConstants.SUBMISSION_QUEUE, groupId = KafkaConstants.GROUP_ID)
    public void listen(SubmissionRequest message) {
        System.out.println("Received Messasge in group foo: " + message);
//        JSONObject jsonObject = new JSONObject(message);
//        JSONArray submissionsArray = jsonObject.getJSONArray("submissions");
//        JSONArray x =  submissionsArray;
//        List<SubmissionRequest.TestCase> submissions = new ArrayList<>();
//
//        for (int i = 0; i < x.length(); i++) {
//            System.out.println("Processing submission: " + x.get(i));
//            JSONObject submissionJson = x.getJSONObject(i);
//            String stdin = submissionJson.getString("stdin");
//            String expectedOutput = submissionJson.getString("expectedOutput");
//            String languageId = submissionJson.getString("languageId");
//            String sourceCode = submissionJson.getString("sourceCode");
//            String token = submissionJson.getString("token");
//            SubmissionRequest.TestCase submission = new SubmissionRequest.TestCase(stdin, expectedOutput, languageId, sourceCode);
//            submission.setToken(token);
//            submissions.add(submission);
//        }

//        SubmissionRequest submissionRequest = new SubmissionRequest(submissions);
        workerService.processTask(message);
    }
}

