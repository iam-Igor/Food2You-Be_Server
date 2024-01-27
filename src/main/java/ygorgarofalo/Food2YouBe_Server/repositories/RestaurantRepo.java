package ygorgarofalo.Food2YouBe_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
}
