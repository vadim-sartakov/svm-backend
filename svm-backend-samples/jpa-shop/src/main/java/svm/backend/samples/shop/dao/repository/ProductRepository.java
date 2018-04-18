package svm.backend.samples.shop.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.samples.shop.dao.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    
}
