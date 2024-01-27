package ygorgarofalo.Food2YouBe_Server.payloads;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(@NotEmpty(message = "Campo obbligatorio per la proprietà email.")
                           String email,

                           @NotEmpty(message = "Campo obbligatorio per la proprietà password.")
                           String password) {
}
