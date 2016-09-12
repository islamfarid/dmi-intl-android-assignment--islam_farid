package com.example.islam.dmitask.allbooks.view;

import com.example.islam.dmitask.models.Book;

import java.util.ArrayList;

/**
 * Created by islam on 10/09/16.
 */
public interface AllBooksView {
    void showProgress();

    void showErrorMessage(String message);

    void setBooks(ArrayList<Book> forecasts);

    void hideProgress();

    void setShouldLoadMore(boolean loadMore);

    void setShouldLoadMore(Book book, boolean loadMore);

}
