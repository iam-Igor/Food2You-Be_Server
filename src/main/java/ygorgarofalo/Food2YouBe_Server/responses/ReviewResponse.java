package ygorgarofalo.Food2YouBe_Server.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewResponse {

    private long id;
    private String username;

    private String message;

    private int rating;


}
