package org.example.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "SubmissionRequest")
public class SubmissionRequest {
    private String _id;
    private List<TestCase> testCases;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TestCase{
        private String stdin;
        private String expectedOutput;
        private String language;
        private String code;
        private String token = UUID.randomUUID().toString();

        public TestCase(String stdin, String expectedOutput, String language, String code) {
            this.stdin = stdin;
            this.expectedOutput = expectedOutput;
            this.language = language;
            this.code = code;
            this.token = UUID.randomUUID().toString();
        }
    }

    public SubmissionRequest(List<TestCase> testCases) {
        this.testCases = testCases;
    }
}
