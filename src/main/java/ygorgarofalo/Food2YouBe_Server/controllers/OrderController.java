package ygorgarofalo.Food2YouBe_Server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ygorgarofalo.Food2YouBe_Server.entities.Order;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.payloads.OrderPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;


    @GetMapping("{id}") // test ok
    @PreAuthorize("hasAuthority('ADMIN')")
    public Order findById(@PathVariable long id) {
        return orderService.findById(id);
    }


    @PostMapping("/new") // test ok
    public Order makeOrder(@RequestBody OrderPayloadDTO body, @AuthenticationPrincipal User user) {
        return orderService.placeOrder(body, user);
    }


    @PatchMapping("/deliver/{id}")
    public void delivered(@PathVariable long id, @AuthenticationPrincipal User user) {
        orderService.setOrderDelivered(id, user);
    }


    //scontrino
    @GetMapping("/print")
    public Order printOrder(@RequestParam("order_id") long id, @AuthenticationPrincipal User user) {
        return orderService.printOrder(id, user);
    }
}
