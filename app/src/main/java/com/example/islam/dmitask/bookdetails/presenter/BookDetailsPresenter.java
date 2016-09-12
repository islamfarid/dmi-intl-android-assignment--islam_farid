package com.example.islam.dmitask.bookdetails.presenter;

import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.bookdetails.bussiness.BookDetailsBussiness;
import com.example.islam.dmitask.bookdetails.view.BookDetailsView;
import com.example.islam.dmitask.db.dto.BookDetail;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by islam on 11/09/16.
 */
public class BookDetailsPresenter {
    WeakReference<BookDetailsView> bookDetailsViewWeakReference;
    @Inject
    BookDetailsBussiness bookDetailsBussiness;

    public void setView(BookDetailsView bookDetailsView) {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
        this.bookDetailsViewWeakReference = new WeakReference(bookDetailsView);
    }

    public void getBookDetails(boolean isLocal, String bookLinkOrBookId) {
        if (isViewAttached()) {
            bookDetailsViewWeakReference.get().showProgress();
        }
        if (!isLocal) {
            getBookDetailsFromWebService(bookLinkOrBookId);
        } else {
            getBookDetailsFromDb(bookLinkOrBookId);
        }

    }

    private boolean isViewAttached() {
        return bookDetailsViewWeakReference != null && bookDetailsViewWeakReference.get() != null;
    }


    public void setBookDetailsBussiness(BookDetailsBussiness bookDetailsBussiness) {
        this.bookDetailsBussiness = bookDetailsBussiness;
    }

    private void getBookDetailsFromWebService(String bookLink) {
        bookDetailsBussiness.getBookDetailsFromWebservice(bookLink).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<BookDetail>() {
            @Override
            public void onCompleted() {
                if (isViewAttached()) {
                    bookDetailsViewWeakReference.get().hideProgress();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    bookDetailsViewWeakReference.get().hideProgress();
                    bookDetailsViewWeakReference.get().showErrorMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(BookDetail bookDetail) {
                if (isViewAttached()) {
                    bookDetailsViewWeakReference.get().showBookDetails(bookDetail);
                }
            }
        });
    }

    private void getBookDetailsFromDb(String bookId) {
        bookDetailsBussiness.getBookDetailsFromDb(bookId).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<BookDetail>() {
            @Override
            public void onCompleted() {
                if (isViewAttached()) {
                    bookDetailsViewWeakReference.get().hideProgress();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    bookDetailsViewWeakReference.get().hideProgress();
                    bookDetailsViewWeakReference.get().showErrorMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(BookDetail bookDetail) {
                if (isViewAttached()) {
                    bookDetailsViewWeakReference.get().showBookDetails(bookDetail);
                }
            }
        });
    }
}
