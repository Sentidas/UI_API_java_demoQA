package app.services;

import app.api.BookApi;
import app.models.AddBookRequestModel;
import app.models.BookDetailsModel;
import app.models.UserSession;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class BookService {
    private final UserSession session;
    private final BookApi bookApi;

    // для моков
    BookService(UserSession session, BookApi bookApi) {
        this.session = session;
        this.bookApi = bookApi;
    }

    public static BookService with(UserSession session) {
        return new BookService(session, new BookApi());
    }

    @Step("Получение списка книг")
    public Response getAllBooks() {
        return bookApi.getStoreBooks();
    }

    @Step("Получение случайной книги из списка книг")
    public BookDetailsModel getRandomBook() {

        List<BookDetailsModel> books = getAllBooks().jsonPath().getList("books", BookDetailsModel.class);

        return books.get(new Random().nextInt(books.size()));
    }

    @Step("Добавление одной книги по ISBN")
    public BookService addSingleBookByIsbn(String isbn) {

        AddBookRequestModel book = AddBookRequestModel.of(session.userId(),
                List.of(AddBookRequestModel.Isbn.of(isbn)));

        bookApi.addBook(session, book);
        return this;
    }

    @Step("Добавление {count} уникальных книг в профиль")
    public List<BookDetailsModel> addBooksToUserProfile(int count) {

        // Проверка: нельзя запросить 0 или отрицательное количество книг
        if (count <= 0) {
            throw new IllegalArgumentException("Количество книг должно быть больше нуля");
        }

        // Получаем все доступные книги из BookStore (в виде объектов BookDetailsModel)
        List<BookDetailsModel> allBooks = new ArrayList<>(
                getAllBooks().jsonPath().getList("books", BookDetailsModel.class));

        // Проверка: нельзя запросить больше книг, чем есть в магазине
        if (count > allBooks.size()) {
            throw new IllegalArgumentException("Запрошено " + count + " книг, но в магазине доступно " + allBooks.size() + " книг");
        }

        // Проверка: магазин вообще не должен быть пустым
        if (allBooks.isEmpty()) {
            throw new IllegalArgumentException("В магазине отсутствуют книги");
        }

        // Перемешиваем список книг для случайного выбора
        Collections.shuffle(allBooks);

        // Выбираем первые count книг из перемешанного списка
        List<BookDetailsModel> selectedBooks = allBooks.stream().limit(count).toList();

        // Создаём пустой список, в который потом добавим ISBN-модели выбранных книг
        List<AddBookRequestModel.Isbn> isbnList = new ArrayList<>();

        // Преобразуем выбранные книги в список ISBN-моделей, нужных для запроса
        // Проходим по каждой выбранной книге, достаём её ISBN и оборачиваем в объект Isbn
        for (BookDetailsModel book : selectedBooks) {
            isbnList.add(AddBookRequestModel.Isbn.of(book.isbn()));
        }

        // Создаём объект запроса на добавление книг, используя фабричный метод
        AddBookRequestModel bookRequest = AddBookRequestModel.of(
                session.userId(),   // ID текущего пользователя
                isbnList            // список обёрнутых ISBN
        );

        bookApi.addBookToProfile(session, bookRequest);

        // Возвращаем список добавленных книг (чтобы тест мог их проверить в UI, если нужно)
        return selectedBooks;
    }


    @Step("Добавление случайных книг для подготовки теста")
    public List<BookDetailsModel> generateRandomBooksForTestContext(int count) {
        List<BookDetailsModel> allBooks = new ArrayList<>(getAllBooks().jsonPath().getList("books", BookDetailsModel.class));

        List<BookDetailsModel> addedBooks = new ArrayList<>();

        if (count == -1) {
            int randomCount = new Random().nextInt(allBooks.size() + 1);
            addedBooks = addBooksToUserProfile(randomCount);
        } else {
            addedBooks = addBooksToUserProfile(count);
        }
        return addedBooks;
    }

    @Step("Получение данных по ISBN книги")
    public BookDetailsModel getBookByIsbn(String isbn) {

        return bookApi.getBookByIsbn(isbn);
    }

    @Step("Удаление всех книг из профиля")
    public BookService deleteAllBooks() {

        bookApi.deleteAllBooks(session);
        return this;
    }
}


