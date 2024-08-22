package org.example.judge1master.repository;

import org.example.common.entity.SubmissionResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubmissionResponseRepository extends MongoRepository<SubmissionResponse, String> {
    long countBySubmissionId(String submissionId);
    List<SubmissionResponse> findBySubmissionId(String submissionId);
}
