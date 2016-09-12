package com.example.islam.dmitask.allbooks.bussiness;

import com.example.islam.dmitask.R;
import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.db.dto.BookDetail;
import com.example.islam.dmitask.db.manager.DbManager;
import com.example.islam.dmitask.models.Book;
import com.example.islam.dmitask.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by islam on 10/09/16.
 */
public class AllBooksBussiness {
    boolean shouldLoadFromDb = true;
    @Inject
    NetworkManager networkManager;
    @Inject
    DbManager dbManager;

    @Inject
    public AllBooksBussiness() {
        DMITaskApplication.getInstance().getApplicationComponent().inject(this);
    }

    public Observable<ArrayList<Book>> getALLBooks(String offset, String count) {
        return networkManager.getBooks(offset, count).map(books -> {
            ArrayList<Book> neededBooks = new ArrayList<>();
            if (books != null && books.size() > 0) {
                neededBooks = books;
            } else {
                if (shouldLoadFromDb) {// to bring cached books only one time
                    shouldLoadFromDb = false;
                    List<BookDetail> cashedBooks = dbManager.loadAllBooks();
                    if (cashedBooks != null) {
                        for (BookDetail bookDetail : cashedBooks) {
                            Book book = new Book();
                            book.setLocalBook(true);
                            book.setId(bookDetail.getId());
                            book.setPrice(bookDetail.getPrice());
                            book.setTitle(bookDetail.getTitle());
                            neededBooks.add(book);
                        }
                    }
                }
            }
            return neededBooks;
        });
    }

    public Observable<Book> getBookByID(String id) {
        return Observable.create(subscriber -> {
            BookDetail bookDetail = dbManager.loadBookById(id);
            if (bookDetail != null) {
                Book book = new Book();
                book.setId(bookDetail.getId());
                book.setTitle(bookDetail.getTitle());
                book.setPrice(bookDetail.getPrice());
                subscriber.onNext(book);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new Throwable(DMITaskApplication.getInstance().getResources().getString(R.string.ppps_some_thing_went_wrong)));
            }

        });
    }
}
