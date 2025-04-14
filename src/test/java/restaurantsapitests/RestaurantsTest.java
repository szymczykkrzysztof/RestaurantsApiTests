package restaurantsapitests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.komy.Main.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class RestaurantsTest {
    static final String RESTAURANTS_URL = BASE_URL + "Restaurants/";

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

    @Test
    void deleteLastRestaurantNotAuthorized() throws JsonProcessingException {
        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(RESTAURANTS_URL);
        response.then().statusCode(200);

        var allIds = response.jsonPath().getString("id");
        ObjectMapper mapper = new ObjectMapper();
        var lastId = mapper.readValue(allIds, List.class).getLast();

        given()
                .when()
                .delete(RESTAURANTS_URL + lastId)
                .then()
                .statusCode(401);
    }
}
