package org.example.judge0slave.util.executor;

import org.example.common.entity.SubmissionRequest;
import org.example.judge0slave.util.DockerCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class JavaExecutor implements LanguageExecutor {

    @Autowired
    private DockerCommandExecutor commandExecutor;

    @Override
    public void execute(SubmissionRequest.TestCase testCase,String token, String id) throws Exception {
        String tempDir = "/tmp/" + System.currentTimeMillis();
        new File(tempDir).mkdirs();

        String filePath = tempDir + "/Main.java";
        Files.write(Paths.get(filePath), testCase.getCode().getBytes());

        String inputPath = tempDir + "/input.txt";
        Files.write(Paths.get(inputPath), testCase.getStdin().getBytes());

        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("-v");
        command.add(tempDir + ":/app");
        command.add("openjdk:latest");
        command.add("bash");
        command.add("-c");
        command.add("javac /app/Main.java && java -cp /app Main < /app/input.txt");

        commandExecutor.executeCommand(command, testCase.getExpectedOutput(), tempDir, token, id);
    }
}
