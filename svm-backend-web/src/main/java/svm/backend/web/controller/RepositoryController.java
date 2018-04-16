package svm.backend.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

@RepositoryRestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public abstract class RepositoryController<T> {
    
    protected final EntityLinks entityLinks;
    protected final PagedResourcesAssembler<Object> pagedResourcesAssembler;


    /*protected PagedResources<?> get(
            Page<Order> page,
            PersistentEntityResourceAssembler resourceAssembler) {

        return pagedResourcesAssembler.toResource(page, resourceAssembler);
       *return page.hasContent() ?
                pagedResourcesAssembler.toResource(page, resourceAssembler) :
                pagedResourcesAssembler.toEmptyResource(page, getManagedType(), null);

    }*/
    
    protected abstract Class<T> getManagedType();
    
}
