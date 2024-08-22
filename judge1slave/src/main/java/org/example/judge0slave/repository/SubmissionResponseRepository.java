package org.example.judge0slave.repository;

import org.example.common.entity.SubmissionResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionResponseRepository extends MongoRepository<SubmissionResponse, String> {
}
