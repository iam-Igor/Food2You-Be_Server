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

//@JsonIgnoreProperties({"user"})
public class Order {


    @Id
    @GeneratedValue
    private long id;

    private LocalDate orderTime;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private boolean paymentAccepted;

    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


}
