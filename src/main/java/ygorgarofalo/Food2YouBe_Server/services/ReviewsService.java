package ygorgarofalo.Food2YouBe_Server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.Review;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.payloads.ReviewPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.repositories.ReviewRepo;
import ygorgarofalo.Food2YouBe_Server.repositories.UserRepo;
import ygorgarofalo.Food2YouBe_Server.responses.ReviewResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewsService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepo;


    public Review findById(long id) {
        return reviewRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    public List<ReviewResponse> findAllReviews() {
        List<Review> reviews = reviewRepo.findAll();
        return mapReviewsToRecords(reviews);
    }

    private List<ReviewResponse> mapReviewsToRecords(List<Review> reviews) {
        List<ReviewResponse> reviewRecords = new ArrayList<>();
        for (Review review : reviews) {
            ReviewResponse reviewRecord = new ReviewResponse(review.getId(), review.getUser().getUsername(), review.getMessage(), review.getRating());
            reviewRecords.add(reviewRecord);
        }
        return reviewRecords;
    }

    public Review saveNewReview(ReviewPayloadDTO payload, User user) {
        Review newReview = new Review();
        if (user != null) {
            User found = userRepo.findById(user.getId()).orElseThrow(() -> new NotFoundException((user.getId())));


            newReview.setMessage(payload.message());
            newReview.setUser(found);
            newReview.setRating(payload.rating());
            found.getReviews().add(newReview);

            userRepo.save(found);
            reviewRepo.save(newReview);
        } else {
            System.out.println("errore");
            System.out.println(user);
        }


        return newReview;
    }

}
