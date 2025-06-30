package app.specs;

import app.models.UserSession;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static app.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class Spec {


    public static RequestSpecification requestSpec() {
        return given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers();
    }

    public static RequestSpecification requestSpecAuth(UserSession session) {

        return requestSpec()
                .header("Authorization", "Bearer " + session.token());
    }

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}
