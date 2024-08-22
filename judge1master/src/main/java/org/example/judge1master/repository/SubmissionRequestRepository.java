package org.example.judge1master.repository;

import org.example.common.entity.SubmissionRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRequestRepository extends MongoRepository<SubmissionRequest, String> {

}
