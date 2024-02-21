package ygorgarofalo.Food2YouBe_Server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ygorgarofalo.Food2YouBe_Server.entities.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {


    @Query("SELECT p FROM Product p WHERE p.restaurant.id = :rest_id")
    List<Product> findByRestaurantId(@Param("rest_id") long rest_id);


    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE %:name%")
    Page<Product> getProductsByName(@Param("name") String name, Pageable pageable);


}
