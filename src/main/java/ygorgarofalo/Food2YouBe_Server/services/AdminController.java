package ygorgarofalo.Food2YouBe_Server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;
import ygorgarofalo.Food2YouBe_Server.payloads.ProductListPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.RestaurantpayloadDTO;

import java.io.IOException;


// questo ocntroller lo uso solo nei casi in cui c'è un conflitto nello shouldnotfilter e i metodi dello
//stesso endpoint che richiedono pre auth.
@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    RestaurantService restaurantService;


    @PostMapping("/restaurant/new") //test ok
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Restaurant saveRestaurant(@RequestBody RestaurantpayloadDTO payload) {
        return restaurantService.saveRestaurant(payload);
    }

    @PatchMapping("/restaurant/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addProducts(@RequestBody ProductListPayloadDTO payloadDTO) {
        return restaurantService.addProducts(payloadDTO);
    }


    @DeleteMapping("/restaurant/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable long id) {
        restaurantService.findByIdAndDelete(id);
    }

    @PatchMapping("/restaurant/upload/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadRestaurantImage(@RequestBody MultipartFile file, @PathVariable long id) throws IOException {
        return restaurantService.uploadRestaurantImage(file, id);
    }
}
