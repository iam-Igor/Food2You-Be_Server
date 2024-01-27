package ygorgarofalo.Food2YouBe_Server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.BadRequestException;
import ygorgarofalo.Food2YouBe_Server.payloads.UserLoginDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.UserPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.responses.GeneralResponse;
import ygorgarofalo.Food2YouBe_Server.responses.TokenResponse;
import ygorgarofalo.Food2YouBe_Server.services.AuthService;
import ygorgarofalo.Food2YouBe_Server.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public TokenResponse login(@RequestBody UserLoginDTO payload) {
        String accessToken = authService.authenticateUser(payload);
        return new TokenResponse(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public GeneralResponse saveUser(@RequestBody(required = false) @Validated UserPayloadDTO body, BindingResult bindingResult) {
        if (body == null) {
            throw new BadRequestException("Il corpo della richiesta non può essere vuoto");
        }
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Errore nel body della richiesta");
        } else {
            User newuser = authService.saveUser(body);
            return new GeneralResponse(newuser.getId());
        }
    }

}
