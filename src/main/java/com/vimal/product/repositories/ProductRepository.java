package com.vimal.product.repositories;

import com.vimal.product.models.Category;
import com.vimal.product.models.Product;
import com.vimal.product.projections.ProductProjection;
import com.vimal.product.projections.ProductTitleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product p);


    @Override
    List<Product> findAll();

    @Query("select distinct p.title as title from Product p")
    Optional<List<ProductTitleProjection>> getTitles();

    Optional<Product> findById(Long id);

    Optional<Product> findByTitle(String title);

    List<Product> findByCategory(Category category);

    List<Product> findAllByCategory_Title(String categoryTitle);

    List<Product> findAllByCategory_Id(Long categoryId);

    List<Product> findByTitleStartingWithAndIdEqualsAndPriceLessThan(String startingWith, Long id, double priceLessThan);

    /*
    title, id of products
    HQL
     */

    @Query("select p.title as title, p.id as id from Product p where p.category.title = :categoryName")
    List<ProductProjection> getTitlesAndIdOfAllProductsWithGivenCategoryName(@Param("categoryName") String categoryName);

    @Query(value = "select * from products p where p.id = 1 and p.title = :productTitle", nativeQuery = true)
    List<ProductProjection> getTitlesAndIdOfAllProductsWithCategoryNameEquals(@Param("productTitle") String productTitle);

}