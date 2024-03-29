package ygorgarofalo.Food2YouBe_Server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.Food2YouBe_Server.entities.CreditCard;
import ygorgarofalo.Food2YouBe_Server.entities.Order;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.payloads.UserPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{id}") // test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findById(@PathVariable long id) {
        return userService.findById(id);
    }


    @DeleteMapping("/me") // test ok
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal User currentUser) {
        userService.findByIdAndDelete(currentUser.getId());
    }


    @GetMapping("/me")// test ok
    public User getProfile(@AuthenticationPrincipal User user) {
        return user;
    }

    @PatchMapping("/me/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String imageUploader(@RequestParam("image") MultipartFile imageFile, @AuthenticationPrincipal User user) throws IOException {
        return userService.uploadAvatarImage(imageFile, user);
    }


    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser(@AuthenticationPrincipal User user, @RequestBody @Validated UserPayloadDTO body) {
        return userService.updateUser(user, body);
    }


    //test ok
    @GetMapping("/orders/me")
    public List<Order> getAllOrders(@AuthenticationPrincipal User user) {
        return userService.getOrdersList(user);
    }


    @GetMapping("/payment/get")
    public CreditCard getCreditCard(@AuthenticationPrincipal User user) {
        return userService.getCreditCard(user);
    }


    @GetMapping("/orders/most_used")
    public Map<String, Long> getCategoriesMostUsedByUser(@AuthenticationPrincipal User user) {
        return userService.countSummaryCategories(user);
    }

}
