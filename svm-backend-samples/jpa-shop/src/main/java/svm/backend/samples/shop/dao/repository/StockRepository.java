package svm.backend.samples.shop.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.samples.shop.dao.entity.Stock;

public interface StockRepository extends PagingAndSortingRepository<Stock, Long> {
    
}
