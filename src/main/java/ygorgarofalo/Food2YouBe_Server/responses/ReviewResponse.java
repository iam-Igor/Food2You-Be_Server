package ygorgarofalo.Food2YouBe_Server.responses;

public class ReviewResponse {

    private long id;
    private String username;

    private String message;

    private int rating;

    public ReviewResponse(long id, String username, String message, int rating) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
