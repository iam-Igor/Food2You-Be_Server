package ygorgarofalo.Food2YouBe_Server.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ygorgarofalo.Food2YouBe_Server.entities.Review;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.payloads.ReviewPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.services.ReviewsService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {


    @Autowired
    private ReviewsService reviewsService;


    @GetMapping("/all")
    public List<Review> findAllReviews() {
        return reviewsService.findAllReviews();
    }


    @GetMapping("/{id}")
    public Review getSingleReview(@PathVariable long id) {
        return reviewsService.findById(id);
    }


    @PostMapping("/new")
    public Review addnewReview(ReviewPayloadDTO payload, @AuthenticationPrincipal User user) {
        return reviewsService.saveNewReview(payload, user);
    }
}
