package svm.backend.data.mongo.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.annotation.PredefinedProcessor;

@Configuration
public class TestConfiguration {
    @MockBean private PredefinedProcessor predefinedProcessor;
}
