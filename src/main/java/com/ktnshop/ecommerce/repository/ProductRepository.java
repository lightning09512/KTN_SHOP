package com.ktnshop.ecommerce.repository;

import com.ktnshop.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySlug(String slug);
    Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Product> findByIsFeaturedTrue();
    List<Product> findByIsNewTrue();
    List<Product> findByCategory_Slug(String slug);
}
