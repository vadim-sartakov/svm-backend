package svm.backend.data.mongo.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import svm.backend.data.mongo.dao.document.SimpleDocument;

public interface SimpleDocumentRepository extends MongoRepository<SimpleDocument, String> {
    
}
