package com.example.crud.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByActiveTrue();
    List<Product> findAllByCategoryIgnoreCaseAndActiveTrue(String category);
    List<Product> findAllByDistributionCenterAndActiveTrue(DistributionCenter distributionCenter);
    List<Product> findAllByDistributionCenterAndCategoryIgnoreCaseAndActiveTrue(DistributionCenter distributionCenter, String category);
    List<Product> findAllByPriceGreaterThanAndActiveTrue(BigDecimal price);

    @Query("select p from Product p where p.active = true and lower(p.name) like lower(concat('%', :name, '%'))")
    List<Product> findByNameContainsIgnoreCaseAndActiveTrue(String name);

    long countByDistributionCenterAndActiveTrue(DistributionCenter distributionCenter);
}
