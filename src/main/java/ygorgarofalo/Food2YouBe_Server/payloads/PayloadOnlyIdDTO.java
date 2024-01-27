package ygorgarofalo.Food2YouBe_Server.payloads;

import jakarta.validation.constraints.NotEmpty;

public record PayloadOnlyIdDTO(
        @NotEmpty(message = "Il campo id non può essere vuoto")
        long id) {
}
