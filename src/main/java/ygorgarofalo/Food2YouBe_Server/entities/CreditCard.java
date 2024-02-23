package ygorgarofalo.Food2YouBe_Server.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"user"})
public class CreditCard {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    private long cardNumber;

    private int cvv;

    private LocalDate expiringDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
