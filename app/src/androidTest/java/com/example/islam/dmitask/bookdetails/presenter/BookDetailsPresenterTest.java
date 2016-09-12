package com.example.islam.dmitask.bookdetails.presenter;

import android.support.test.InstrumentationRegistry;

import com.example.islam.dmitask.bookdetails.bussiness.BookDetailsBussiness;
import com.example.islam.dmitask.bookdetails.view.BookDetailsView;
import com.example.islam.dmitask.db.dto.BookDetail;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by islam on 12/09/16.
 */
public class BookDetailsPresenterTest {
    BookDetailsPresenter bookDetailsPresenter;
    BookDetailsView bookDetailsView;
    BookDetailsBussiness bookDetailsBussiness;

    @Before
    public void init() {
        System.setProperty("dexmaker.dexcache", InstrumentationRegistry.getInstrumentation().getTargetContext().getCacheDir().getPath());
        bookDetailsPresenter = new BookDetailsPresenter();
        bookDetailsBussiness = mock(BookDetailsBussiness.class);
        bookDetailsView = mock(BookDetailsView.class);
        bookDetailsPresenter.setView(bookDetailsView);
        bookDetailsPresenter.setBookDetailsBussiness(bookDetailsBussiness);
    }

    @Test
    public void testWhenGetBookDetailsFromDbIsCalled_BookDetailsViewShowProgressISCalled() {
        when(bookDetailsBussiness.getBookDetailsFromDb(null)).thenReturn(Observable.create((Observable.OnSubscribe<BookDetail>) sub -> {
            //do nothing we just test if method is called
        }));
        bookDetailsPresenter.getBookDetails(true, null);
        verify(bookDetailsView, times(1)).showProgress();
    }

    @Test
    public void testWhenGetBookDetailsFromWebserviceIsCalled_BookDetailsViewShowProgressISCalled() {
        when(bookDetailsBussiness.getBookDetailsFromWebservice(null)).thenReturn(Observable.create((Observable.OnSubscribe<BookDetail>) sub -> {
            //do nothing we just test if method is called
        }));
        bookDetailsPresenter.getBookDetails(false, null);
        verify(bookDetailsView, times(1)).showProgress();
    }

    @Test
    public void testWhenGetBookDetailsFromDbError_BookDetailsViewHideProgressISCalled() {
        doNothing().when(bookDetailsView).showProgress();
        when(bookDetailsBussiness.getBookDetailsFromDb(null)).thenReturn(Observable.create((Observable.OnSubscribe<BookDetail>) sub -> {
            sub.onError(new Throwable());
            verify(bookDetailsView, times(1)).hideProgress();
        }));
        bookDetailsPresenter.getBookDetails(true, null);
    }

    @Test
    public void testWhenGetBookDetailsFromWebserviceError_BookDetailsViewHideProgressISCalled() {
        doNothing().when(bookDetailsView).showProgress();
        when(bookDetailsBussiness.getBookDetailsFromWebservice(null)).thenReturn(Observable.create((Observable.OnSubscribe<BookDetail>) sub -> {
            sub.onError(new Throwable());
            verify(bookDetailsView, times(1)).hideProgress();
        }));
        bookDetailsPresenter.getBookDetails(false, null);
    }

    @Test
    public void testWhenGetBookDetailsFromWebserviceCompleted_AllBooksViewHideProgressISCalled() {
        doNothing().when(bookDetailsView).showProgress();
        when(bookDetailsBussiness.getBookDetailsFromWebservice(null)).thenReturn(Observable.create((Observable.OnSubscribe<BookDetail>) sub -> {
            sub.onCompleted();
            verify(bookDetailsView, times(1)).hideProgress();
        }));
        bookDetailsPresenter.getBookDetails(false, null);
    }

    @Test
    public void testWhenGetBookDetailsFromDbCompleted_AllBooksViewHideProgressISCalled() {
        doNothing().when(bookDetailsView).showProgress();
        when(bookDetailsBussiness.getBookDetailsFromDb(null)).thenReturn(Observable.create((Observable.OnSubscribe<BookDetail>) sub -> {
            sub.onCompleted();
            verify(bookDetailsView, times(1)).hideProgress();
        }));
        bookDetailsPresenter.getBookDetails(true, null);
    }
}