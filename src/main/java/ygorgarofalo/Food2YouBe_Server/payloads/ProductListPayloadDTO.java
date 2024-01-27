package ygorgarofalo.Food2YouBe_Server.payloads;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ProductListPayloadDTO(

        @NotEmpty(message = "Il campo productIds non può essere vuoto.")
        List<Long> productIds,

        @NotEmpty(message = "Il campo restaurantId non può essere vuoto.")
        Long restaurantId
) {
}
