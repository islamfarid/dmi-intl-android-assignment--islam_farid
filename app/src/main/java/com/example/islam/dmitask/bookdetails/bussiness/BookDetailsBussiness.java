package com.example.islam.dmitask.bookdetails.bussiness;

import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.db.dto.BookDetail;
import com.example.islam.dmitask.db.manager.DbManager;
import com.example.islam.dmitask.network.NetworkManager;
import com.example.islam.dmitask.network.ServiceGenerator;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by islam on 11/09/16.
 */
public class BookDetailsBussiness {
    @Inject
    NetworkManager networkManager;
    @Inject
    DbManager dbManager;

    @Inject
    public BookDetailsBussiness() {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
    }

    public Observable<BookDetail> getBookDetailsFromWebservice(String bookLink) {
        return networkManager.getBookDetails(ServiceGenerator.API_BASE_URL.substring(0, ServiceGenerator.API_BASE_URL.length() - 1) + bookLink);
    }

    public Observable<BookDetail> getBookDetailsFromDb(String bookId) {
        return Observable.create(subscriber -> {
            subscriber.onNext(dbManager.loadBookById(bookId));
            subscriber.onCompleted();
        });
    }
}
