package svm.backend.dao.migration;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import svm.backend.dao.repository.UserRepository;

public class LiquibaseTask implements CustomTaskChange {
    
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    
    @Override
    public void execute(Database database) throws CustomChangeException {
        
    }

    @Override
    public String getConfirmationMessage() {
        return "Custom task executed successfully";
    }

    @Override
    public void setUp() throws SetupException {
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {
        
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }
    
}
