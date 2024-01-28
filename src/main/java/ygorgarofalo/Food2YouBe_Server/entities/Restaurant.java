package ygorgarofalo.Food2YouBe_Server.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"orderList", "productList"})
public class Restaurant {


    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String streetAddress;

    private String city;

    private double longitude;

    private double latitude;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList;

    private String imageUrl;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orderList;

}
