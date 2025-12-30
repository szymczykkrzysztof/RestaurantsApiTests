package restaurantsapitests;

import com.github.javafaker.Faker;
import com.komy.ConfigManager;
import com.komy.models.CreateRestaurantDto;
import com.komy.models.User;
import helpers.Utilities;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.komy.Main.BASE_URL;
import static io.restassured.RestAssured.given;

public class AdminUserTest {
    static final String RESTAURANTS_URL = BASE_URL + "Restaurants/";
    String bearerToken;
    Utilities  utils = new Utilities();
    Faker faker = new Faker();

    @BeforeEach
    void authorize() {
        var user = new User("admin@test.com", ConfigManager.get("ADMIN_USER_PASSWORD"));
        bearerToken = utils.getToken(user);
    }

    @Test
    void createAndDeleteRestaurant() {

        var cityName = faker.address().cityName();

        var name = faker.company().name();
        var category = "Italian";
        var description = String.format(
                "Nasza restauracja to wyjątkowe miejsce w sercu %s, gdzie tradycyjne smaki spotykają się z nowoczesną kuchnią. "
                        + "Specjalizujemy się w %s i oferujemy %s atmosferę idealną na każdą okazję.",
                cityName, faker.food().dish().toLowerCase(), faker.commerce().productName().toLowerCase()
        );
        var street = faker.address().streetAddress();
        var postalCode = "52-200";
        var hasDelivery = true;
        var contactEmail = "fancy@email.test";
        var contactNumber = "123-123-123";
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
