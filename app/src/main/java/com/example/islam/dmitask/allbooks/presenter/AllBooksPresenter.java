package com.example.islam.dmitask.allbooks.presenter;

import com.example.islam.dmitask.allbooks.bussiness.AllBooksBussiness;
import com.example.islam.dmitask.allbooks.view.AllBooksView;
import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.models.Book;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by islam on 10/09/16.
 */
public class AllBooksPresenter {
    WeakReference<AllBooksView> allBooksViewWeakReference;
    @Inject
    AllBooksBussiness allBooksBussiness;

    public void setView(AllBooksView allBooksView) {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
        this.allBooksViewWeakReference = new WeakReference(allBooksView);
    }

    public void loadAllBooks(String offset, boolean showProgress, String count) {
        if (isViewAttached() && showProgress) {
            allBooksViewWeakReference.get().showProgress();
        }
        allBooksBussiness.getALLBooks(offset, count).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe(new Subscriber<ArrayList<Book>>() {
            @Override
            public void onCompleted() {
                if (isViewAttached()) {
                    allBooksViewWeakReference.get().hideProgress();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    allBooksViewWeakReference.get().hideProgress();
                    allBooksViewWeakReference.get().showErrorMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(ArrayList<Book> books) {
                if (isViewAttached()) {
                    allBooksViewWeakReference.get().hideProgress();
                    if (books != null && books.size() > 0) {
                        allBooksViewWeakReference.get().setBooks(books);
                    } else {
                        allBooksViewWeakReference.get().setShouldLoadMore(false);
                    }
                }
            }
        });
    }

    public void getBookById(String id) {
        allBooksBussiness.getBookByID(id).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Book>() {
            @Override
            public void onCompleted() {
//do nthing
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    allBooksViewWeakReference.get().showErrorMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(Book bookDetail) {
                allBooksViewWeakReference.get().setShouldLoadMore(bookDetail, false);

            }
        });
    }

    private boolean isViewAttached() {
        return allBooksViewWeakReference != null && allBooksViewWeakReference.get() != null;
    }


    public void setAllBooksBussiness(AllBooksBussiness allBooksBussiness) {
        this.allBooksBussiness = allBooksBussiness;
    }
}
