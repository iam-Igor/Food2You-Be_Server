package ygorgarofalo.Food2YouBe_Server.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String lastname;

    private String username;

    private String email;

    private String password;

    private String address;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private Role role;


}
