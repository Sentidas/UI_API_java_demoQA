package app.services;

import app.api.BookApi;
import app.models.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BookService {
    private final UserSession session;
    private final BookApi bookApi;


    BookService(UserSession session, BookApi bookApi) {
        this.session = session;
        this.bookApi = bookApi;
    }

    public static BookService with(UserSession session) {
        return new BookService(session, new BookApi());
    }

    @Step("Получение списка книг из магазина")
    public BooksDetailsModel getStoreBooks() {
        return bookApi.getStoreBooks();
    }

    @Step("Получение списка книг из магазина")
    public List<BookDetailsModel> getStoreBookList() {
        return bookApi.getStoreBooks().books();
    }

    @Step("Получение случайной книги из списка книг")
    public BookDetailsModel getRandomBook() {

        List<BookDetailsModel> books = getStoreBookList();

        return books.get(new Random().nextInt(books.size()));
    }

    @Step("Добавление одной книги по ISBN")
    public AddBookResponseModel addSingleBookByIsbn(String isbn) {

        AddBookRequestModel book = AddBookRequestModel.of(session.userId(),
                List.of(AddBookRequestModel.Isbn.of(isbn)));

        return bookApi.addBookToProfile(session, book);

    }

    @Step("Добавление одной книги c несуществующим ISBN")
    public AddBookErrorResponseModel addSingleBookByIsbnWithError(String isbn) {

        AddBookRequestModel request = AddBookRequestModel.of(session.userId(),
                List.of(AddBookRequestModel.Isbn.of(isbn)));

        return bookApi.addBookToProfileWithError(session, request);

    }

    @Step("Добавление книги без авторизационных данных")
    public AddBookErrorResponseModel addSingleBookByIsbnWithoutLogin(String isbn) {

        AddBookRequestModel request = AddBookRequestModel.of(session.userId(),
                List.of(AddBookRequestModel.Isbn.of(isbn)));

        return bookApi.addBookToProfileWithoutLogin(request);

    }

    @Step("Повторная попытка добавления книги по ISBN")
    public Response repeatAddBookByIsbn(String isbn) {
        AddBookRequestModel request = AddBookRequestModel.of(
                session.userId(),
                List.of(AddBookRequestModel.Isbn.of(isbn))
        );
        return bookApi.addBookToProfileRaw(session, request);
    }

    @Step("Добавление {count} уникальных книг в профиль")
    public List<BookDetailsModel> addBooksToUserProfile(int count) {

        // Проверка: нельзя запросить 0 или отрицательное количество книг
        if (count <= 0) {
            throw new IllegalArgumentException("Количество книг должно быть больше нуля");
        }

        // Получаем все доступные книги из магазина
        List<BookDetailsModel> booksStore = getStoreBookList();

        // Проверка: магазин не должен быть пустым
        if (booksStore.isEmpty()) {
            throw new IllegalArgumentException("В магазине отсутствуют книги");
        }

        // Проверка: нельзя запросить больше книг, чем есть в магазине
        if (count > booksStore.size()) {
            throw new IllegalArgumentException("Запрошено " + count + " книг, но в магазине доступно " + booksStore.size() + " книг");
        }

        // Получаем заданное количество случайных книги из всех книг магазина
        List<BookDetailsModel> selectedBooks = getRandomBooks(booksStore, count);
        List<AddBookRequestModel.Isbn> isbnList = toIsbnList(selectedBooks);

        AddBookRequestModel bookRequest = AddBookRequestModel.of(
                session.userId(),
                isbnList
        );

        bookApi.addBookToProfile(session, bookRequest);

        return selectedBooks;
    }

    private List<AddBookRequestModel.Isbn> toIsbnList(List<BookDetailsModel> books) {
        List<AddBookRequestModel.Isbn> isbns = new ArrayList<>();
        for (BookDetailsModel book : books) {
            isbns.add(AddBookRequestModel.Isbn.of(book.isbn()));
        }
        return isbns;
    }

    @Step("Добавление {count} уникальных книг в профиль")
    public AddBookResponseModel addBooksToUserProfileApi(List<BookDetailsModel> books) {

        List<AddBookRequestModel.Isbn> isbnList = toIsbnList(books);

        AddBookRequestModel bookRequest = AddBookRequestModel.of(
                session.userId(),
                isbnList
        );

        return bookApi.addBookToProfile(session, bookRequest);
    }


    @Step("Добавление 0 в профиль")
    public AddBookErrorResponseModel addEmptyBookListToProfile() {

        List<AddBookRequestModel.Isbn> isbnList = new ArrayList<>();

        AddBookRequestModel bookRequest = AddBookRequestModel.of(
                session.userId(),
                isbnList
        );

        return bookApi.addBookToProfileWithError(session, bookRequest);

    }

     List<BookDetailsModel> getRandomBooks(List<BookDetailsModel> allBooks, int count) {
        // Перемешиваем список книг для случайного выбора
        Collections.shuffle(allBooks);

        // Выбираем первые count книг из перемешанного списка
        List<BookDetailsModel> selectedBooks = allBooks.stream().limit(count).toList();
        return selectedBooks;
    }

    @Step("Получение случайного количества книг из магазина")
    public List<BookDetailsModel> getRandomBooksFromStore() {

        List<BookDetailsModel> allBooks = getStoreBookList();

        int count = new Random().nextInt(allBooks.size());

        return getRandomBooks(allBooks, count);
    }

    @Step("Получение {count} случайных книг из магазина")
    public List<BookDetailsModel> getRandomBooksFromStore(int count) {

        List<BookDetailsModel> allBooks = getStoreBookList();
        List<BookDetailsModel> selectedBooks = getRandomBooks(allBooks, count);
        return selectedBooks;
    }


    @Step("Генерация случайных книг для подготовки теста")
    public List<BookDetailsModel> generateRandomBooksForTestContext(int count) {
        List<BookDetailsModel> allBooks = getStoreBookList();

        if (count == -1) {
            int randomCount = new Random().nextInt(allBooks.size() + 1);
            return addBooksToUserProfile(randomCount);
        } else {
            return addBooksToUserProfile(count);
        }
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


