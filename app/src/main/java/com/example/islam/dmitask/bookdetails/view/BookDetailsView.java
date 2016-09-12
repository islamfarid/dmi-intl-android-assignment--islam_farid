package com.example.islam.dmitask.bookdetails.view;

import com.example.islam.dmitask.db.dto.BookDetail;

/**
 * Created by islam on 11/09/16.
 */
public interface BookDetailsView {
    void showProgress();

    void hideProgress();

    void showBookDetails(BookDetail bookDetail);

    void showErrorMessage(String message);
}
