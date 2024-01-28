package ygorgarofalo.Food2YouBe_Server.payloads;

import jakarta.validation.constraints.NotEmpty;

public record ProductpayloadDTO(
        @NotEmpty(message = "Il campo name non può essere vuoto")
        String name,
        @NotEmpty(message = "Il campo price non può essere vuoto")
        double price,
        @NotEmpty(message = "Il campo ingredients non può essere vuoto")
        String ingredients,
        @NotEmpty(message = "Il campo description non può essere vuoto")
        String description,
        @NotEmpty(message = "Il campo calories non può essere vuoto")
        double calories,

        @NotEmpty(message = "Il campo type non può essere vuoto")
        String type


) {
}
