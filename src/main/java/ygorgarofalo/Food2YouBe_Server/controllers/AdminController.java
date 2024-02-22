package ygorgarofalo.Food2YouBe_Server.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.Food2YouBe_Server.entities.Order;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.payloads.ProductListPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.ProductUpdateDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.ProductpayloadDTO;
import ygorgarofalo.Food2YouBe_Server.payloads.RestaurantpayloadDTO;
import ygorgarofalo.Food2YouBe_Server.services.OrderService;
import ygorgarofalo.Food2YouBe_Server.services.ProductService;
import ygorgarofalo.Food2YouBe_Server.services.RestaurantService;
import ygorgarofalo.Food2YouBe_Server.services.UserService;

import java.io.IOException;


// questo ocntroller lo uso solo nei casi in cui c'è un conflitto nello shouldnotfilter e i metodi dello
//stesso endpoint che richiedono pre auth.
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ProductService productService;


    @Autowired
    private OrderService orderService;


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


    //modifica di un ristorante

    @PatchMapping("/restaurant/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant updaterestaurant(@PathVariable long id, @RequestBody RestaurantpayloadDTO body) {
        return restaurantService.findByIdAndUpdate(id, body);

    }

    @PostMapping("/products/new") //test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product addNewProductAndAssignToRestaurant(@RequestBody ProductpayloadDTO body) {
        return productService.saveProduct(body);
    }

    @DeleteMapping("/restaurant/{id}") //test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable long id) {
        restaurantService.findByIdAndDelete(id);
    }

    @PatchMapping("/product/upload/{id}") // test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadProductImage(@RequestParam("image") MultipartFile file, @PathVariable long id) throws IOException {
        return productService.uploadProductImage(file, id);
    }


    @PatchMapping("/restaurant/upload/{id}") // test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadRestaurantImage(@RequestParam("image") MultipartFile file, @PathVariable long id) throws IOException {
        return restaurantService.uploadRestaurantImage(file, id);
    }


    @PatchMapping("products/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product updateProduct(@PathVariable long id, @RequestBody ProductUpdateDTO body) {
        return productService.findByIdAndUpdate(id, body);
    }


    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable long id) {
        productService.findByIdAndDelete(id);
    }


    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Order> getOrders(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String order) {
        return orderService.getOrders(page, size, order);
    }


    @GetMapping("/users") // test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String order) {
        return userService.getUsers(page, size, order);
    }
}
