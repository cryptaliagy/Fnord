package slng.fnord.Structures.Service;

public class Ratings {
    int ratingValue;
    String comment;
    String raterName;

    public Ratings(int ratingValue, String comment, String raterName) {
        this.ratingValue = ratingValue;
        this.comment = comment;
        this.raterName = raterName;
    }

    public Ratings(){
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRaterName() {
        return raterName;
    }

    public void setRaterName(String raterName) {
        this.raterName = raterName;
    }
}
