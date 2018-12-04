package slng.fnord;

import org.junit.Test;

import slng.fnord.Helpers.Enums.UserTypes;
import slng.fnord.Structures.Ratings;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.User;

import static com.google.common.truth.Truth.assertThat;

public class RatingTests {
    @Test
    public void rateSP() {
        ServiceProvider serviceProvider = new ServiceProvider("glitt073@uottawa.ca", "1337Potato");
        Ratings rating = new Ratings(3, "I like pie better than this service provider", "coolperson123@fakemail.com");
        //System.out.println(rating);
        serviceProvider.addRating(rating);
        assertThat(serviceProvider.getRatings().get(0) ).isEqualTo(rating);
        assertThat(serviceProvider.getAverageRating()).isEqualTo((float)rating.getRatingValue()); //only 1 rating so should be equal
    }

    @Test
    public void rate7SP(){
        // overflow rating array and check math
        ServiceProvider serviceProvider = new ServiceProvider("glitt073@uottawa.ca", "1337Potato");
        int sum = 0;
        for (int i=1; i<8; i++){
            Ratings rating = new Ratings((i%4)+1, "The guy wouldn't give me a pony", "ponylover2000@fakemail.com");
            serviceProvider.addRating(rating);
            sum+=(i%4)+1;
        }
        assertThat(serviceProvider.getRatings().size() ).isEqualTo(5); // should be 5 at most
        assertThat(serviceProvider.getAverageRating()).isEqualTo((float)sum/7);
    }
}
