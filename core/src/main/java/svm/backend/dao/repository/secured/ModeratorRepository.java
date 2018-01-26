/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.dao.repository.secured;

import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.access.annotation.Secured;
import svm.backend.dao.entity.UserRole;
import svm.backend.dao.repository.CrudRepository;

@NoRepositoryBean
@Secured({ UserRole.ADMIN, UserRole.MODERATOR })
public interface ModeratorRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
    
}
