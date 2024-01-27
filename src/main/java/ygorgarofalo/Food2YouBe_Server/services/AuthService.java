package ygorgarofalo.Food2YouBe_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.Role;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.BadRequestException;
import ygorgarofalo.Food2YouBe_Server.exceptions.UnauthorizedException;
import ygorgarofalo.Food2YouBe_Server.payloads.UserLoginDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.UserPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.repositories.UserRepo;
import ygorgarofalo.Food2YouBe_Server.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepo userRepo;


    //login
    public String authenticateUser(UserLoginDTO body) {
        User user = userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }


    //register
    public User saveUser(UserPayloadDTO payload) {
        User newUser = new User();
        newUser.setRole(Role.USER);
        newUser.setLastname(payload.lastname());
        newUser.setName(payload.name());
        newUser.setEmail(payload.email());
        newUser.setUsername(payload.username());
        newUser.setPassword(bcrypt.encode(payload.password()));
        if (userRepo.existsByEmail(payload.email())) {
            throw new BadRequestException("L'email " + payload.email() + " è gia presente nel sistema.");
        } else if (userRepo.existsByUsername(payload.username())) {
            throw new BadRequestException("Lo username " + payload.username() + " è gia presente nel sistema.");
        } else {
            return userRepo.save(newUser);
        }
    }


}
