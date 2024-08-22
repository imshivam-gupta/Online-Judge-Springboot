package org.example.judge0slave.service;

import lombok.extern.slf4j.Slf4j;
import org.example.common.entity.SubmissionRequest;
import org.example.judge0slave.util.executor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class WorkerService {
    private final Map<String, LanguageExecutor> executors = new HashMap<>();

    @Autowired
    private CPPExecutor cppExecutor;

    public WorkerService(CPPExecutor cppExecutor) {
        this.executors.put("cpp", cppExecutor);
    }

//    static{
//        executors.put("cpp", cppExecutor);
//        executors.put("java", new JavaExecutor());
//        executors.put("rust", new RustExecutor());
//        executors.put("js",new JavaScriptExecutor());
//    }

    public void processTask(SubmissionRequest submissionRequest) {
        System.out.println(submissionRequest.get_id());
        for (SubmissionRequest.TestCase testCase : submissionRequest.getTestCases()) {
            try {
                LanguageExecutor executor = executors.get(testCase.getLanguage());
                if (executor != null) {
                    executor.execute(testCase, testCase.getToken(), submissionRequest.get_id());
                } else {
                    throw new UnsupportedOperationException("Unsupported language: " + testCase.getLanguage());
                }
            } catch (Exception e) {
                log.error("Error while executing task", e);
            }
        }
    }
}
