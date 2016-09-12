package com.example.islam.dmitask.bookadding.bussiness;

import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.db.dto.BookDetail;
import com.example.islam.dmitask.db.manager.DbManager;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by islam on 11/09/16.
 */
public class BookAddingBussiness {
    @Inject
    DbManager dbManager;

    @Inject
    public BookAddingBussiness() {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
    }

    public Observable<Boolean> newBook(String id, String price, String auther, String title, String image) {
        return rx.Observable.create(
                subscriber -> {
                    BookDetail bookDetail = new BookDetail();
                    bookDetail.setId(id);
                    bookDetail.setTitle(title);
                    bookDetail.setPrice(Double.parseDouble(price));
                    bookDetail.setAuthor(auther);
                    bookDetail.setImage(image);
                    dbManager.newBook(bookDetail);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                });
    }
}

