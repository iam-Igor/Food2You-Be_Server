package ygorgarofalo.Food2YouBe_Server.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ygorgarofalo.Food2YouBe_Server.entities.CreditCard;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.payloads.CreditCardDTO;
import ygorgarofalo.Food2YouBe_Server.services.CreditCardService;

@RestController
@RequestMapping("/payment")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard saveNewCreditCard(@AuthenticationPrincipal User user, @RequestBody CreditCardDTO body) {
        return creditCardService.saveCreditCard(user, body);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteCard(@AuthenticationPrincipal User user, @PathVariable long id) {
        creditCardService.findByIdAndDelete(user, id);
    }
}
