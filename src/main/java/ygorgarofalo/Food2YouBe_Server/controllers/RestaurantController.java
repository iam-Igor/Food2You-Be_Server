package ygorgarofalo.Food2YouBe_Server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.Restaurant;
import ygorgarofalo.Food2YouBe_Server.services.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;


    @GetMapping // test ok
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getRestaurants();
    }


    @GetMapping("/{id}") // test ok
    public Restaurant findById(@PathVariable long id) {
        return restaurantService.findById(id);
    }


    @GetMapping("/city/{city}")// test ok
    public List<Restaurant> getRestaurantsByCity(@PathVariable String city) {
        return restaurantService.getRestaurantsByCityName(city);
    }


    @GetMapping("/{id}/products")
    public List<Product> getProductList(@PathVariable long id) {
        return restaurantService.getRestaurantProductList(id);
    }


    @GetMapping("/search")
    public List<Restaurant> getByCityAndSummary(@RequestParam String city, @RequestParam String summary) {
        return restaurantService.getRestaurantsByCityAndSummary(city, summary);
    }


}
