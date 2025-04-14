package restaurantsapitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static com.komy.Main.BASE_URL;
import static org.hamcrest.Matchers.lessThan;

public class RestaurantsTest {
    static final String RESTAURANTS_URL = BASE_URL+"Restaurants/";

    @Test
    void getAllRestaurants() {
        RestAssured.get(RESTAURANTS_URL)
                .then()
                .statusCode(200)
                .time(lessThan(60L), TimeUnit.SECONDS);
    }

    @Test
    void getRestaurantByIdNotAuthorized() {
        RestAssured.get(RESTAURANTS_URL + "1")
                .then()
                .statusCode(401);
    }
}
