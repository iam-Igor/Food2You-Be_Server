package ygorgarofalo.Food2YouBe_Server.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"orderList"})
public class Restaurant {


    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String streetAddress;

    private String city;

    private double longitude;

    private double latitude;

    @OneToMany(mappedBy = "restaurant")
    private List<Product> productList;

    private String imageUrl;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orderList;

}
