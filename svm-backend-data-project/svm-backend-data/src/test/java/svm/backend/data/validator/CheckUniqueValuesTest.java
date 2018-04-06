package svm.backend.data.validator;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.repository.support.Repositories;

public class CheckUniqueValuesTest {
        
    private final Repositories repositories = Mockito.mock(Repositories.class);
    private final ConfigurableBeanFactory beanFactory = Mockito.mock(ConfigurableBeanFactory.class);
    private final CheckUniqueValues instance = new CheckUniqueValues(repositories, beanFactory);
    
    @Test
    public void testSingleFields() {
        
        
        
    }

    @Test
    public void testFieldSet() {
        
    }
    
}
