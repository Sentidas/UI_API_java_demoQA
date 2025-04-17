package tests.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.AddBookRequestModel;
import models.BookDetailsModel;
import models.UserSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static specs.Spec.*;

public class BookApi {
    UserSession session;

    public BookApi(UserSession session) {
        this.session = session;
    }

    @Step("Получение списка книг")
    public Response getAllBooks() {
        return given(requestSpec)
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    @Step("Получение случайной книги из списка книг")
    public BookDetailsModel getRandomBook() {

        List<BookDetailsModel> books = getAllBooks().jsonPath().getList("books", BookDetailsModel.class);

        return books.get(new Random().nextInt(books.size()));
    }

    @Step("Добавление одной книги по ISBN")
    public BookApi addSingleBookByIsbn(String isbn) {

        AddBookRequestModel book = new AddBookRequestModel();
        book.setUserId(session.userId());

        AddBookRequestModel.Isbn bookIsbn = new AddBookRequestModel.Isbn();
        bookIsbn.setIsbn(isbn);
        book.setCollectionOfIsbns(List.of(bookIsbn));

        given(requestSpecAuth(session))
                .body(book)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)

                .statusCode(201)
                .extract().response();
        return this;
    }

    @Step("Добавление {count} уникальных книг в профиль")
    public List<BookDetailsModel> addBooksToUserProfile(int count) {

        // Получаем все доступные книги
        List<BookDetailsModel> allBooks = new ArrayList<>(
                getAllBooks().jsonPath().getList("books", BookDetailsModel.class));

        // Перемешиваем список книг для случайности
        Collections.shuffle(allBooks);

        // Выбираем первые count книг из перемешанного списка
        List<BookDetailsModel> selectedBooks = allBooks.stream().limit(count).toList();


        // Преобразуем выбранные книги в список ISBN
        List<AddBookRequestModel.Isbn> isbnList = selectedBooks.stream()
                .map(book -> {
                    AddBookRequestModel.Isbn bookIsbn = new AddBookRequestModel.Isbn();
                    bookIsbn.setIsbn(book.isbn());
                    return bookIsbn;
                }).toList();

        // Формируем запрос для добавления книг в профиль
        AddBookRequestModel bookRequest = new AddBookRequestModel();
        // Устанавливаем userId в запрос
        bookRequest.setUserId(session.userId());
        // Устанавливаем список ISBN в запрос
        bookRequest.setCollectionOfIsbns(isbnList);


        given(requestSpecAuth(session))
                .body(bookRequest)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)

                .statusCode(201);

        return selectedBooks;
    }

    @Step("Добавление случайных книг для подготовки теста")
    public void addRandomBooksForSetup(int count) {
        List<BookDetailsModel> allBooks = new ArrayList<>(getAllBooks().jsonPath().getList("books", BookDetailsModel.class));

        if (count == -1) {
            int randomCount = new Random().nextInt(allBooks.size() + 1);
            addBooksToUserProfile(randomCount);
        } else {
            addBooksToUserProfile(count);
        }
    }

    @Step("Получение данных по ISBN книги")
    public BookDetailsModel getBookByIsbn(String isbn) {

        return given(requestSpec)
                .queryParam("ISBN", isbn)
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .spec(responseSpec)
                .extract().as(BookDetailsModel.class);
    }

    @Step("Удаление всех книг из профиля")
    public BookApi deleteAllBooks() {

        given(requestSpecAuth(session))
                .queryParam("UserId", session.userId())
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(204);
        return this;
    }
}


