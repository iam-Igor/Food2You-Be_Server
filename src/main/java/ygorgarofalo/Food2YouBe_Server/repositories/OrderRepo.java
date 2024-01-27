package ygorgarofalo.Food2YouBe_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ygorgarofalo.Food2YouBe_Server.entities.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
