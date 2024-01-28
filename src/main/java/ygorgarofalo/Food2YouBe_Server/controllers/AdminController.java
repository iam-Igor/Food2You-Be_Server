package ygorgarofalo.Food2YouBe_Server.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;
import ygorgarofalo.Food2YouBe_Server.payloads.ProductListPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.ProductpayloadDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.RestaurantpayloadDTO;
import ygorgarofalo.Food2YouBe_Server.services.ProductService;
import ygorgarofalo.Food2YouBe_Server.services.RestaurantService;

import java.io.IOException;


// questo ocntroller lo uso solo nei casi in cui c'è un conflitto nello shouldnotfilter e i metodi dello
//stesso endpoint che richiedono pre auth.
@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    RestaurantService restaurantService;

    @Autowired
    private ProductService productService;


    @PostMapping("/restaurant/new") //test ok
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Restaurant saveRestaurant(@RequestBody RestaurantpayloadDTO payload) {
        return restaurantService.saveRestaurant(payload);
    }

    @PatchMapping("/restaurant/add")
    // test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addProducts(@RequestBody ProductListPayloadDTO payloadDTO) {
        return restaurantService.addProducts(payloadDTO);
    }


    @PostMapping("/products/new") //test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product addNewProductAndAssigntoRestaurant(@RequestBody ProductpayloadDTO body) {
        return productService.saveProduct(body);
    }

    @DeleteMapping("/restaurant/{id}") //test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable long id) {
        restaurantService.findByIdAndDelete(id);
    }

    @PatchMapping("/restaurant/upload/{id}") // test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadRestaurantImage(@RequestParam("image") MultipartFile file, @PathVariable long id) throws IOException {
        return restaurantService.uploadRestaurantImage(file, id);
    }


}
