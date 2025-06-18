package app.models;

public record BookDetailsModel(
        String isbn,
        String title,
        String subTitle,
        String author,
        String publish_date,
        String publisher,
        int pages,
        String description,
        String website
){}




