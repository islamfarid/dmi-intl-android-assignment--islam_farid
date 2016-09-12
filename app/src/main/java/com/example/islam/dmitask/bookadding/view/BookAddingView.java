package com.example.islam.dmitask.bookadding.view;

/**
 * Created by islam on 11/09/16.
 */
public interface BookAddingView {
    void showProgress();

    void hideProgress();

    void dismiss();

    void showErrorMessage(String message);
}
