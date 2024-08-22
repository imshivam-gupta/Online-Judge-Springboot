package org.example.judge0slave.util.executor;

import org.example.common.entity.SubmissionRequest;
import org.example.judge0slave.util.DockerCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CPPExecutor implements LanguageExecutor {

    @Autowired
    private DockerCommandExecutor commandExecutor;

    public static String createTempDirectory() throws IOException {
        Path tempDirectory = Files.createTempDirectory("cpp_executor_");
        return tempDirectory.toString();
    }

    @Override
    public void execute(SubmissionRequest.TestCase testCase, String token, String id) throws Exception {
        String tempDir = createTempDirectory();
        String filePath = Paths.get(tempDir, "Main.cpp").toString();
        Files.write(Paths.get(filePath), testCase.getCode().getBytes());

        String inputPath = Paths.get(tempDir, "input.txt").toString();
        Files.write(Paths.get(inputPath), testCase.getStdin().getBytes());

        // Convert Windows paths to Unix-style paths for Docker
        String volumePath = tempDir.replace("\\", "/");

        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("-v");
        command.add(volumePath + ":/app");
        command.add("gcc:latest");

        // Use quotes around the command to ensure it's executed as a single command inside the container
        command.add("bash");
        command.add("-c");
        command.add("g++ /app/Main.cpp -o /app/Main && /app/Main < /app/input.txt");

        // Execute the command
        commandExecutor.executeCommand(command, testCase.getExpectedOutput(), tempDir, token, id);
    }
}
