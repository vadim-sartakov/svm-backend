package svm.backend.data.mongo.core.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import svm.backend.data.mongo.core.dao.document.SimpleDocument;

public interface SimpleDocumentRepository extends MongoRepository<SimpleDocument, String> {
    
}
