package com.example.islam.dmitask.bookadding.presenter;

import android.support.test.InstrumentationRegistry;

import com.example.islam.dmitask.bookadding.bussiness.BookAddingBussiness;
import com.example.islam.dmitask.bookadding.view.BookAddingView;

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
public class BookAddingPresenterTest {
    BookAddingPresenter bookAddingPresenter;
    BookAddingView bookAddingView;
    BookAddingBussiness bookAddingBussiness;

    @Before
    public void init() {
        System.setProperty("dexmaker.dexcache", InstrumentationRegistry.getInstrumentation().getTargetContext().getCacheDir().getPath());
        bookAddingPresenter = new BookAddingPresenter();
        bookAddingBussiness = mock(BookAddingBussiness.class);
        bookAddingView = mock(BookAddingView.class);
        bookAddingPresenter.setView(bookAddingView);
        bookAddingPresenter.setBookAddingBussiness(bookAddingBussiness);
    }

    @Test
    public void testWhenAddNewBookCalled_BookAddingViewShowProgressISCalled() {
        when(bookAddingBussiness.newBook(null, null, null, null, null)).thenReturn(Observable.create((Observable.OnSubscribe<Boolean>) sub -> {
            //do nothing we just test if method is called
        }));
        bookAddingPresenter.newBook(null, null, null, null, null);
        verify(bookAddingView, times(1)).showProgress();
    }

    @Test
    public void testWhenAddNewBookError_BookAddingViewHideProgressISCalled() {
        doNothing().when(bookAddingView).showProgress();
        when(bookAddingBussiness.newBook(null, null, null, null, null)).thenReturn(Observable.create((Observable.OnSubscribe<Boolean>) sub -> {
            sub.onError(new Throwable());
            verify(bookAddingView, times(1)).hideProgress();
        }));
        bookAddingPresenter.newBook(null, null, null, null, null);
    }

    @Test
    public void testWhenAddNewBookCompleted_BookAddingViewHideProgressISCalled() {
        doNothing().when(bookAddingView).showProgress();
        when(bookAddingBussiness.newBook(null, null, null, null, null)).thenReturn(Observable.create((Observable.OnSubscribe<Boolean>) sub -> {
            sub.onCompleted();
            verify(bookAddingView, times(1)).hideProgress();
        }));
        bookAddingPresenter.newBook(null, null, null, null, null);
    }

    @Test
    public void testWhenAddNewBookCompleted_BookAddingViewHDismissIsCalled() {
        doNothing().when(bookAddingView).showProgress();
        when(bookAddingBussiness.newBook(null, null, null, null, null)).thenReturn(Observable.create((Observable.OnSubscribe<Boolean>) sub -> {
            sub.onCompleted();
            verify(bookAddingView, times(1)).dismiss();
        }));
        bookAddingPresenter.newBook(null, null, null, null, null);
    }
}