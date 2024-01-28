package ygorgarofalo.Food2YouBe_Server.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@JsonIgnoreProperties({"user"})
public class Order {


    @Id
    @GeneratedValue
    private long id;

    private LocalDate orderTime;


    // aggiungere enum qui
    private OrderStatus orderStatus;

    private boolean paymentAccepted;

    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Product> productList;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


}
