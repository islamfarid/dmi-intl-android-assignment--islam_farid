package com.example.islam.dmitask.db.manager;

import com.example.islam.dmitask.db.dto.BookDetail;

import java.util.List;

/**
 * Created by islam on 7/25/16.
 */
public interface DbManager {
    void newBook(BookDetail newBook);

    List<BookDetail> loadAllBooks();

    BookDetail loadBookById(String id);

    void removeBook(BookDetail bookDetail);
}
