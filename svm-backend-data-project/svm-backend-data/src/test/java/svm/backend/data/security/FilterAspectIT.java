package svm.backend.data.security;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.data.Application;
import svm.backend.data.config.AspectConfig;
import svm.backend.data.config.BaseMessagesConfig;
import svm.backend.data.dao.MultipleFieldEntity;
import svm.backend.data.dao.MultipleFieldEntityRepository;
import svm.backend.data.dao.SingleFieldEntity;
import svm.backend.data.dao.SingleFieldEntityRepository;
import svm.backend.data.migration.service.MigrationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Import({ BaseMessagesConfig.class, AspectConfig.class })
public class FilterAspectIT {
    
    @MockBean private MigrationRepository migrationRepository;
    @Autowired private SingleFieldEntityRepository singleFieldRepository;
    @Autowired private MultipleFieldEntityRepository multipleFieldRepository;
    
    @Test
    public void testFilter() {
        
        singleFieldRepository.save(SingleFieldEntity.builder().uniqueNumber(0).build());
        singleFieldRepository.save(SingleFieldEntity.builder().uniqueNumber(1).build());
        singleFieldRepository.save(SingleFieldEntity.builder().uniqueNumber(2).build());
        assertThat(singleFieldRepository.findAll(null, new PageRequest(0, 20)).getTotalElements(), is(1l));
        
        multipleFieldRepository.save(MultipleFieldEntity.builder().firstName("Bill").build());
        multipleFieldRepository.save(MultipleFieldEntity.builder().firstName("John").build());
        multipleFieldRepository.save(MultipleFieldEntity.builder().firstName("Mark").build());
        assertThat(multipleFieldRepository.findAll(null, new PageRequest(0, 20)).getTotalElements(), is(3l));
        
    }
    
}
