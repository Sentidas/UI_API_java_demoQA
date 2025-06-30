package app.models;

import lombok.Data;

import java.util.List;

@Data
public class AddBookResponseModel {

    List<Isbn> books;

    @Data
    public static class Isbn {
        private String isbn;
    }
}
