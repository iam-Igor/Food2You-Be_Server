package ygorgarofalo.Food2YouBe_Server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {


    @Id
    @GeneratedValue
    private long id;

    private LocalDate orderTime;

    private OrderStatus orderStatus;

    private boolean paymentAccepted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Product> productList;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


}