package restaurantsapitests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.lessThan;

public class RestaurantsTest {
    static final String BASE_URL = "https://restaurants-api-zpp1.onrender.com/api/restaurants/";

    @Test
    void getAllRestaurants() {
        RestAssured.get(BASE_URL)
                .then()
                .statusCode(200)
                .time(lessThan(60L), TimeUnit.SECONDS);
    }

    @Test
    void getRestaurantByIdNotAuthorized() {
        RestAssured.get(BASE_URL + "1")
                .then()
                .statusCode(401);
    }
}
