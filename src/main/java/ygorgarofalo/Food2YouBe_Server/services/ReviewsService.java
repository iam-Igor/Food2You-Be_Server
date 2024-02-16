package ygorgarofalo.Food2YouBe_Server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.Review;
import ygorgarofalo.Food2YouBe_Server.entities.User;
import ygorgarofalo.Food2YouBe_Server.exceptions.NotFoundException;
import ygorgarofalo.Food2YouBe_Server.payloads.ReviewPayloadDTO;
import ygorgarofalo.Food2YouBe_Server.repositories.ReviewRepo;
import ygorgarofalo.Food2YouBe_Server.repositories.UserRepo;

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


    public List<Review> findAllReviews() {
        return reviewRepo.findAll();
    }


    public Review saveNewReview(ReviewPayloadDTO payload, User user) {

        Review newReview = new Review();
        newReview.setMessage(payload.message());
        newReview.setUser(user);

        user.getReviews().add(newReview);

        userRepo.save(user);

        return reviewRepo.save(newReview);
    }

}
