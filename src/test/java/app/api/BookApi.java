package app.api;

import app.models.*;
import app.specs.Spec;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.List;

import static app.specs.Spec.*;
import static io.restassured.RestAssured.given;

public class BookApi {

    public AddBookResponseModel addBookToProfile(UserSession session, AddBookRequestModel bookRequest) {
        return given(requestSpecAuth(session))
                .body(bookRequest)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract().as(AddBookResponseModel.class);
    }

    public AddBookErrorResponseModel addBookToProfileWithoutLogin(AddBookRequestModel bookRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(bookRequest)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(401)
                .extract().as(AddBookErrorResponseModel.class);
    }

    public AddBookErrorResponseModel addBookToProfileWithError(UserSession session, AddBookRequestModel bookRequest) {
        return given(requestSpecAuth(session))
                .contentType(ContentType.JSON)
                .body(bookRequest)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .extract().as(AddBookErrorResponseModel.class);
    }

    public Response addBookToProfileRaw(UserSession session, AddBookRequestModel bookRequest) {
        return given(requestSpecAuth(session))
                .body(bookRequest)
                .post("/BookStore/v1/Books")
                .then()
                .statusCode(400)
                .log().body()
                .extract().response();
    }

    public BookDetailsModel getBookByIsbn(String isbn) {
        return given(Spec.requestSpec())
                .queryParam("ISBN", isbn)
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .spec(responseSpec)
                .extract().as(BookDetailsModel.class);
    }

    public  void deleteAllBooks(UserSession session) {
        given(requestSpecAuth(session))
                .queryParam("UserId", session.userId())
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(204);
    }

    public BooksDetailsModel getStoreBooks() {
        return given(Spec.requestSpec())
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .extract().as(BooksDetailsModel.class);
    }
}
