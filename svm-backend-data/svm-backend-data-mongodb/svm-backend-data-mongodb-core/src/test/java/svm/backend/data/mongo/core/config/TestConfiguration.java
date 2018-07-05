package svm.backend.data.mongo.core.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.core.annotation.PredefinedProcessor;

@Configuration
public class TestConfiguration {
    @MockBean private PredefinedProcessor predefinedProcessor;
}
