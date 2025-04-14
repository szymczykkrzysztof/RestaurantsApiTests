package helpers;

import com.komy.models.User;
import io.restassured.http.ContentType;

import static com.komy.Main.BASE_URL;
import static io.restassured.RestAssured.given;

public class Utils {
    final String LOGIN_URL = BASE_URL + "identity/login";

    public String getToken(User user) {
        var response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(LOGIN_URL)
                .body()
                .jsonPath();
        var token = response.getString("accessToken");
        return "Bearer " + token;
    }
}
