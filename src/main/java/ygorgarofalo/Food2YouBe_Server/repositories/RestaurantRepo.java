package ygorgarofalo.Food2YouBe_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {


    List<Restaurant> findByCityIgnoreCase(String city);

}
