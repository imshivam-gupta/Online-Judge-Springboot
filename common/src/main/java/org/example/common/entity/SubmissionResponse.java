package org.example.common.entity;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "SubmissionResponse")
public class SubmissionResponse {
    private String _id;
    private String token;
    private String submissionId;
    private Integer statusCode;
    private String output;
    private String error;

    @CreatedDate
    private String createdAt;

    private Double executionTime;
    private Double memory;
}
