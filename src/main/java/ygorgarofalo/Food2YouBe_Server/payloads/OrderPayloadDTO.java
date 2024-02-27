package ygorgarofalo.Food2YouBe_Server.payloads;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderPayloadDTO(

        // lo user non lo passo in quanto lo troverò gia tramite AuthPrincipal
        // la data sarà sempre quella attuale quindi localdate.now
        //lo status iniziale sarà sempre in_preparazione
        // lo stato del pagamento sarà true

        @NotEmpty(message = "Il campo productIds non può essere vuoto")
        List<Long> productIds,

        // per aggiungere dei prodotti verrà richiesto lato front end di passare un array di ID dei prodotti
        // che verranno poi cercati a db successivamente aggiunti all'ordine

        @NotEmpty(message = "Il campo restaurantId non può essere vuoto")
        Long restaurantId,


        @NotEmpty(message = "Il campo userAdrress non può essere vuoto")
        String userAddress

) {
}
