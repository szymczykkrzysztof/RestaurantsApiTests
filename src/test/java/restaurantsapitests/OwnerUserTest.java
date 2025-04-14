package restaurantsapitests;

import com.github.javafaker.Faker;
import com.komy.ConfigManager;
import com.komy.models.CreateRestaurantDto;
import com.komy.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.komy.Main.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OwnerUserTest {
    final String LOGIN_URL = BASE_URL + "identity/login";
    static final String RESTAURANTS_URL = BASE_URL + "Restaurants/";
    String bearerToken;
    String restaurantId;

    @BeforeEach
    void authorize() {
        var user = new User("owner@test.com", ConfigManager.get("OWNER_USER_PASSWORD"));
        System.out.println(LOGIN_URL);
        var response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(LOGIN_URL)
                .body()
                .jsonPath();
        var token = response.getString("accessToken");
        bearerToken = "Bearer " + token;
    }
    @Test
    void createAndDeleteRestaurant() {
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
                .header("Authorization", bearerToken)
                .contentType(ContentType.JSON)
                .body(restaurant)
                .when()
                .post(RESTAURANTS_URL);
        response.then().statusCode(201);

        restaurantId = Arrays.stream(response.header("location").split("/")).toList().getLast();
        var createdRestaurantResponse = given()
                .header("Authorization", bearerToken)
                .contentType(ContentType.JSON)
                .when()
                .get(RESTAURANTS_URL + restaurantId);
        createdRestaurantResponse.then().statusCode(200);
        var responseBody = createdRestaurantResponse.body();

        assert responseBody.jsonPath().getString("name").equals(name);
        assert responseBody.jsonPath().getString("id").equals(restaurantId);
        assert responseBody.jsonPath().getString("category").equals(category);
        assert responseBody.jsonPath().getString("description").equals(description);
        assert responseBody.jsonPath().getString("postalCode").equals(postalCode);
        assert responseBody.jsonPath().getString("street").equals(street);
        assert responseBody.jsonPath().getString("city").equals(cityName);
        assertTrue(responseBody.jsonPath().getBoolean("hasDelivery"));

        given()
                .header("Authorization", bearerToken)
                .when()
                .delete(RESTAURANTS_URL + restaurantId)
                .then()
                .statusCode(204);
    }
}
