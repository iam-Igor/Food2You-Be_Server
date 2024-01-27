package ygorgarofalo.Food2YouBe_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ygorgarofalo.Food2YouBe_Server.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
