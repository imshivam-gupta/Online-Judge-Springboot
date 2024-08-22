package org.example.judge0slave.util;

import org.example.common.entity.SubmissionResponse;
import org.example.judge0slave.repository.SubmissionResponseRepository;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.*;

@Component
public class DockerCommandExecutor {

    private final SubmissionResponseRepository submissionResponseRepository;

    public DockerCommandExecutor(SubmissionResponseRepository submissionResponseRepository) {
        this.submissionResponseRepository = submissionResponseRepository;
    }

    public void executeCommand(List<String> command, String expectedOutput, String tempDir, String testCaseToken, String subReqId) throws Exception {
        SubmissionResponse submissionResponse = new SubmissionResponse();
        submissionResponse.setSubmissionId(subReqId);
        submissionResponse.setToken(testCaseToken);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        System.out.println("Executing command: " + String.join(" ", command));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        StringBuilder output = new StringBuilder();
        StringBuilder errorOutput = new StringBuilder();

        int exitCode = process.waitFor();

        String line;
        while ((line = stdOutput.readLine()) != null) {
            output.append(line).append("\n");
        }

        String result = output.toString().trim();

        if (exitCode == 0) {
            System.out.println("Output:\n" + result);

            if (result.equals(expectedOutput)) {
                System.out.println("Test passed!");
                submissionResponse.setOutput(result);
                submissionResponse.setError("");
                submissionResponse.setStatusCode(0);
            } else {
                System.out.println("Test failed! Expected: " + expectedOutput);
                submissionResponse.setOutput(result);
                submissionResponse.setError("Test failed! Expected: " + expectedOutput);
                submissionResponse.setStatusCode(1);
            }
        } else {
            System.out.println("Execution failed with exit code " + exitCode);
            errorOutput.append(result);
            submissionResponse.setOutput("");
            submissionResponse.setError(errorOutput.toString());
            submissionResponse.setStatusCode(exitCode);
//            throw new RuntimeException("Execution failed with exit code " + exitCode + "\nErrors:\n" + result);
        }

        submissionResponseRepository.save(submissionResponse);
        cleanup(tempDir);
    }

    private void cleanup(String tempDir) {
        File directory = new File(tempDir);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.out.println("Failed to delete file: " + file.getName());
                }
            }
        }

        boolean deleted = directory.delete();
        if (!deleted) {
            System.out.println("Failed to delete directory: " + directory.getName());
        }
    }
}
