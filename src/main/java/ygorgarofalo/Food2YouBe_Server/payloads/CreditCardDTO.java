package ygorgarofalo.Food2YouBe_Server.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreditCardDTO(
        @NotEmpty
        String fullName,
        @NotEmpty
        Long cardNumber,
        @NotEmpty
        @Size(min = 3, message = "Il cvv non può contenere più di 3 caratteri")
        int cvv,
        @NotEmpty
        LocalDate expiringDate) {
}
