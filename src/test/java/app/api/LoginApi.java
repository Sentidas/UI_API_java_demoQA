package app.api;

import app.models.UserSession;

import java.util.Map;

import static app.specs.Spec.requestSpec;
import static app.specs.Spec.responseSpec;
import static io.restassured.RestAssured.given;

public class LoginApi {

    public UserSession login(Map<String, String> authData) {
        return given(requestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(UserSession.class);
    }
}
