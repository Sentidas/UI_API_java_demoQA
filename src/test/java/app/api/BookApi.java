package app.api;

import app.models.AddBookRequestModel;
import app.models.BookDetailsModel;
import app.models.UserSession;
import app.specs.Spec;
import io.restassured.response.Response;

import static app.specs.Spec.*;
import static io.restassured.RestAssured.given;

public class BookApi {

    public void addBookToProfile(UserSession session, AddBookRequestModel bookRequest) {
        // Отправляем POST-запрос для добавления книг в профиль пользователя
        given(requestSpecAuth(session))
                .body(bookRequest)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(201); // Проверяем, что операция прошла успешно
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

    public void addBook(UserSession session, AddBookRequestModel book) {
        given(requestSpecAuth(session))
                .body(book)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract().response();
    }

    public Response getStoreBooks() {
        return given(Spec.requestSpec())
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .extract().response();
    }
}
