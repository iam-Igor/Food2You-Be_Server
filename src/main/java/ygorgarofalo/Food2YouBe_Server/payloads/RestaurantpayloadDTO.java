package ygorgarofalo.Food2YouBe_Server.payloads;

import jakarta.validation.constraints.NotEmpty;

public record RestaurantpayloadDTO(


        @NotEmpty(message = "Il campo name non può essere vuoto")
        String name,
        @NotEmpty(message = "Il campo streetAddress non può essere vuoto")
        String streetAddress,
        @NotEmpty(message = "Il campo city non può essere vuoto")
        String city,
        @NotEmpty(message = "Il campo longitude non può essere vuoto")
        double longitude,
        @NotEmpty(message = "Il campo latitude non può essere vuoto")
        double latitude,

        @NotEmpty(message = "Il campo summary non può essere vuoto")
        String summary


        // l'avatar url verrà caricato in seguito con un altro metodo

        // la lista di prodotti verrà aggiunta in seguito nel ristorante

) {
}
