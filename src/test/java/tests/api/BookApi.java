package tests.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.AddBookRequestModel;

import java.util.List;
import java.util.Random;

import static Specs.Spec.requestSpec;
import static Specs.Spec.responseSpec;
import static io.restassured.RestAssured.given;

public class BookApi {
    public static String nameBook;
    public static String idBook;

    @Step("Получение списка книг для добавления в корзину")
    public void getBooksAll() {
        Response responseGetBooksList = given(requestSpec)
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    @Step("Получение id книги из списка книг")
    public String getIDBook() {
        Response responseGetBooksList = given(requestSpec)
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .extract().response();
        List<String> bookList = responseGetBooksList.path("books.isbn");
        idBook = bookList.get(new Random().nextInt(bookList.size()));
        System.out.println("Book is " + idBook);
        return idBook;
    }

    @Step("Добавление книги")
    public void addBook() {

        AddBookRequestModel bookData = new AddBookRequestModel();
        bookData.setUserId(AuthApi.userId);
        AddBookRequestModel.Isbn isbn = new AddBookRequestModel.Isbn();
        isbn.setIsbn(getIDBook());
        bookData.setCollectionOfIsbns(List.of(isbn));
        Response responseAddBook = given(requestSpec)
                .header("Authorization", "Bearer " + AuthApi.token)
                .body(bookData)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract().response();
    }

    @Step("Получение данных по id книги")
    public String getNameBookWithId(String isbn) {
        Response responseGetBooksList = given(requestSpec)
                .queryParam("ISBN", isbn)
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .spec(responseSpec)
                .extract().response();
        return nameBook = responseGetBooksList.path("title");
    }
}
