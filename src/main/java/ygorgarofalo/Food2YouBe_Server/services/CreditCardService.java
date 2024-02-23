package ygorgarofalo.Food2YouBe_Server.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.CreditCard;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.payloads.CreditCardDTO;
import ygorgarofalo.Food2YouBe_Server.repositories.CreditCardRepo;

import java.time.LocalDate;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepo creditCardRepo;

    @Autowired
    private UserService userService;


    public CreditCard saveCreditCard(User user, CreditCardDTO body) {
        CreditCard created = new CreditCard();
        User found = userService.findById(user.getId());

        created.setCardNumber(body.cardNumber());
        created.setCvv(body.cvv());
        created.setFullName(body.fullName());
        created.setExpiringDate(LocalDate.of(body.expiringDate().getYear(), body.expiringDate().getMonth(), 1));
        created.setUser(found);
        return creditCardRepo.save(created);
    }

    public CreditCard findById(long id) {
        return creditCardRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public void findByIdAndDelete(User user, long id) {
        CreditCard found = this.findById(id);
        User foundUser = userService.findById(user.getId());
        if (foundUser.getCreditCard().getId().equals(id)) {
            found.setUser(null);
            creditCardRepo.delete(found);
        } else {
            throw new IllegalArgumentException("La carta non appartiene all'utente specificato.");
        }
    }

}


