package svm.backend.web.controller;

import com.querydsl.core.types.Predicate;
import java.io.Serializable;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.HttpHeadersPreparer;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.DefaultedPageable;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RepositoryRestController
public abstract class RepositoryController<T,
        R extends PagingAndSortingRepository<T, ? extends Serializable> & QueryDslPredicateExecutor<T>> {
    
    @Autowired protected RepositoryRestConfiguration config;
    @Autowired protected RepositoryEntityLinks entityLinks;
    @Autowired protected HttpHeadersPreparer headerPreparer;
    @Autowired protected PagedResourcesAssembler<Object> pagedResourcesAssembler;
    @Autowired protected Validator validator;

    @SuppressWarnings("unchecked")
    protected ResponseEntity<PagedResources<?>> get(
            DefaultedPageable pageable,
            Predicate predicate,
            PersistentEntityResourceAssembler resourceAssembler,
            R repository) {
                
        Link baseLink = entityLinks.linkToPagedResource(getManagedType(),
                pageable.isDefault() ? null : pageable.getPageable());
        
        Page<T> page = repository.findAll(predicate, pageable.getPageable());
        
        PagedResources<?> pagedResources = page.hasContent() ?
                pagedResourcesAssembler.toResource((Page<Object>) page, resourceAssembler) :
                pagedResourcesAssembler.toEmptyResource(page, getManagedType(), baseLink);
        
        return ResponseEntity.ok(pagedResources);

    }
    
    protected ResponseEntity<ResourceSupport> getOne(Predicate predicate,
            PersistentEntityResourceAssembler resourceAssembler,
            R repository) {
        
        T entity = repository.findOne(predicate);
        return entity == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(resourceAssembler.toFullResource(entity)) ;
        
    } 
        
    protected ResponseEntity<ResourceSupport> post(Resource<T> resource,
            PersistentEntityResourceAssembler resourceAssembler,
            CrudRepository<T, ? extends Serializable> repository) {
        
        T entity = resource.getContent();
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (violations.size() > 0)
            throw new ConstraintViolationException(violations);
        
        T saved = repository.save(entity);
        
        PersistentEntityResource persistentEntityResource = resourceAssembler.toFullResource(saved);
        HttpHeaders headers = headerPreparer.prepareHeaders(persistentEntityResource);
        String selfLink = resourceAssembler.getSelfLinkFor(saved).getHref();
        headers.setLocation(new UriTemplate(selfLink).expand());
        
        return new ResponseEntity<>(config.returnBodyOnCreate("") ? persistentEntityResource : null,
                headers, HttpStatus.CREATED);
        
    }
    
    // TODO: make patch and put
    
    protected abstract Class<T> getManagedType();
    
}
