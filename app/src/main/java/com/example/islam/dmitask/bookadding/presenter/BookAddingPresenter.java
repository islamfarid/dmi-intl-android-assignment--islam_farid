package com.example.islam.dmitask.bookadding.presenter;

import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.bookadding.bussiness.BookAddingBussiness;
import com.example.islam.dmitask.bookadding.view.BookAddingView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by islam on 11/09/16.
 */
public class BookAddingPresenter {
    WeakReference<BookAddingView> bookAddingViewWeakReference;
    @Inject
    BookAddingBussiness bookAddingBussiness;

    public void setView(BookAddingView bookAddingView) {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
        this.bookAddingViewWeakReference = new WeakReference(bookAddingView);
    }

    public void newBook(String id, String price, String auther, String title, String image) {
        if (isViewAttached()) {
            bookAddingViewWeakReference.get().showProgress();
        }
        //to check is tablet or not and define number of elements
        bookAddingBussiness.newBook(id, price, auther, title, image).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                if (isViewAttached()) {
                    bookAddingViewWeakReference.get().hideProgress();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    bookAddingViewWeakReference.get().hideProgress();
                    bookAddingViewWeakReference.get().showErrorMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (isViewAttached()) {
                    bookAddingViewWeakReference.get().dismiss();
                }
            }
        });

    }

    private boolean isViewAttached() {
        return bookAddingViewWeakReference != null && bookAddingViewWeakReference.get() != null;
    }


    public void setBookAddingBussiness(BookAddingBussiness bookAddingBussiness) {
        this.bookAddingBussiness = bookAddingBussiness;
    }
}
