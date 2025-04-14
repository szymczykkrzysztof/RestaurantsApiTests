package restaurantsapitests;

import com.github.javafaker.Faker;
import com.komy.ConfigManager;
import com.komy.models.CreateRestaurantDto;
import com.komy.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.komy.Main.BASE_URL;
import static io.restassured.RestAssured.given;

public class AuthorizedUserTest {
    final String LOGIN_URL = BASE_URL + "identity/login";
    static final String RESTAURANTS_URL = BASE_URL + "Restaurants/";
    String bearerToken;

    @BeforeEach
    void authorize() {
        var user = new User("user@test.com", ConfigManager.get("TEST_USER_PASSWORD"));
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
    void getRestaurantById() {
        given()
                .header("Authorization", bearerToken)
                .contentType(ContentType.JSON)
                .when()
                .get(RESTAURANTS_URL + 1)
                .then()
                .statusCode(200);
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
        var postalCode = faker.address().zipCode();
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
        response.then().statusCode(403);
    }
}