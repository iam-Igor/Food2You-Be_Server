package ygorgarofalo.Food2YouBe_Server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ygorgarofalo.Food2YouBe_Server.entities.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {


    @Query("SELECT o FROM Order o WHERE o.id= :id AND o.user.id= :userId")
    Order getOrderByuserIdandOrderId(@Param("id") long id, @Param("userId") long userId);

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);


}
