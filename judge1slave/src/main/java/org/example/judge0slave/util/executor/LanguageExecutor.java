package org.example.judge0slave.util.executor;

import org.example.common.entity.SubmissionRequest;

public interface LanguageExecutor {
    void execute(SubmissionRequest.TestCase testCase, String token, String id) throws Exception;
}

