package svm.backend.data.security;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.Application;
import svm.backend.data.dao.MultipleFieldEntity;
import svm.backend.data.dao.MultipleFieldEntityRepository;
import svm.backend.data.dao.SingleFieldEntity;
import svm.backend.data.dao.SingleFieldEntityRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class FilterAspectIT {
    
    @Autowired private SingleFieldEntityRepository singleFieldRepository;
    @Autowired private MultipleFieldEntityRepository multipleFieldRepository;
    
    private SingleFieldEntity firstEntity, secondEntity;
    
    @Before
    public void setUp() {
        firstEntity = singleFieldRepository.save(SingleFieldEntity.builder().uniqueNumber(0).build());
        secondEntity = singleFieldRepository.save(SingleFieldEntity.builder().uniqueNumber(1).build());
        singleFieldRepository.save(SingleFieldEntity.builder().uniqueNumber(2).build());
        multipleFieldRepository.save(MultipleFieldEntity.builder().firstName("Bill").build());
        multipleFieldRepository.save(MultipleFieldEntity.builder().firstName("John").build());
        multipleFieldRepository.save(MultipleFieldEntity.builder().firstName("Mark").build());
    }
    
    @Test
    public void testFindAll() {
        assertThat(singleFieldRepository.findAll(null, new PageRequest(0, 20)).getTotalElements(), is(1l));
        assertThat(multipleFieldRepository.findAll(null, new PageRequest(0, 20)).getTotalElements(), is(3l));    
    }
    
    @Test
    public void testFindOne() {
        assertThat(singleFieldRepository.findOne(firstEntity.getId()), is(nullValue()));
        assertThat(singleFieldRepository.findOne(secondEntity.getId()), is(notNullValue()));
    }
    
}
