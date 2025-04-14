package restaurantsapitests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.komy.models.CreateRestaurantDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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

    @Test
    void createRestaurantNotAuthorized() throws JsonProcessingException {
        Faker faker = new Faker();
        var cityName = faker.address().cityName();

        var name = faker.company().name();
        var category = faker.food().ingredient();
        var description = String.format(
                "Nasza restauracja to wyjątkowe miejsce w sercu %s, gdzie tradycyjne smaki spotykają się z nowoczesną kuchnią. "
                        + "Specjalizujemy się w %s i oferujemy %s atmosferę idealną na każdą okazję.",
                cityName, faker.food().dish().toLowerCase(), faker.commerce().productName().toLowerCase()
        );
        var street = faker.address().streetAddress();
        var postalCode = "52-200";
        var hasDelivery = true;
        var contactEmail = "fancy@email.test";
        var contactNumber = faker.phoneNumber().phoneNumber();
        var restaurant = new CreateRestaurantDto(postalCode, street, cityName, contactNumber, contactEmail, hasDelivery, category, description, name);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(restaurant)
                .when()
                .post(RESTAURANTS_URL);
        response.then().statusCode(401);
    }
}
