package ygorgarofalo.Food2YouBe_Server.payloads;

import jakarta.validation.constraints.NotEmpty;

public record ReviewPayloadDTO(

        @NotEmpty(message = "Il campo message non può essere vuoto.")
        String message,

        @NotEmpty(message = "Il campo rating non può essere vuoto.")
        int rating
) {
}
