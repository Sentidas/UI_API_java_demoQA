package models;

import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Data
public class AddBookRequestModel {

    private String userId;
    private List<Isbn> collectionOfIsbns;

    public static AddBookRequestModel of(String userId, List<Isbn> isbns) {
        AddBookRequestModel model = new AddBookRequestModel();
        model.setUserId(userId);
        model.setCollectionOfIsbns(isbns);
        return model;
    }

    @Data
    public static class Isbn {
        private String isbn;

        public static Isbn of(String isbn) {
            Isbn bookIsbn = new Isbn();
            bookIsbn.setIsbn(isbn);
            return bookIsbn;
        }
    }
}
