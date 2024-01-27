package ygorgarofalo.Food2YouBe_Server.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {


    @Id
    @GeneratedValue
    private long id;

    private String name;

    private double price;

    private String ingredients;

    private double calories;

    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "restautant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
