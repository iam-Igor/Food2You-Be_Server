package ygorgarofalo.Food2YouBe_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {


    List<Restaurant> findByCityIgnoreCase(String city);


    @Query("SELECT r FROM Restaurant r WHERE r.city LIKE :city and r.summary = :summary")
    List<Restaurant> selectByCityAndSummary(@Param("city") String city, @Param("summary") String summary);

}
