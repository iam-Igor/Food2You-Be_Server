package ygorgarofalo.Food2YouBe_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ygorgarofalo.Food2YouBe_Server.entities.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
