package com.example.islam.dmitask.allbooks.presenter;

import android.support.test.InstrumentationRegistry;

import com.example.islam.dmitask.allbooks.bussiness.AllBooksBussiness;
import com.example.islam.dmitask.allbooks.view.AllBooksView;
import com.example.islam.dmitask.models.Book;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import rx.Observable;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by islam on 12/09/16.
 */
public class AllBooksPresenterTest {
    AllBooksPresenter allBooksPresenter;
    AllBooksView allBooksView;
    AllBooksBussiness allBooksBussiness;

    @Before
    public void init() {
        System.setProperty("dexmaker.dexcache", InstrumentationRegistry.getInstrumentation().getTargetContext().getCacheDir().getPath());
        allBooksPresenter = new AllBooksPresenter();
        allBooksBussiness = mock(AllBooksBussiness.class);
        allBooksView = mock(AllBooksView.class);
        allBooksPresenter.setView(allBooksView);
        allBooksPresenter.setAllBooksBussiness(allBooksBussiness);
    }

    @Test
    public void testWhenloadBooksCalled_AllBookViewShowProgressISCalled() {
        when(allBooksBussiness.getALLBooks(null, null)).thenReturn(Observable.create((Observable.OnSubscribe<ArrayList<Book>>) sub -> {
            //do nothing we just test if method is called
        }));
        allBooksPresenter.loadAllBooks(null, true, null);
        verify(allBooksView, times(1)).showProgress();
    }

    @Test
    public void testWhenloadAllBookssError_AllBooksViewHideProgressISCalled() {
        doNothing().when(allBooksView).showProgress();
        when(allBooksBussiness.getALLBooks(null, null)).thenReturn(Observable.create((Observable.OnSubscribe<ArrayList<Book>>) sub -> {
            sub.onError(new Throwable());
            verify(allBooksView, times(1)).hideProgress();
        }));
        allBooksPresenter.loadAllBooks(null, true, null);
    }

    @Test
    public void testWhenloadAllBooksCompleted_AllBooksViewHideProgressISCalled() {
        doNothing().when(allBooksView).showProgress();
        when(allBooksBussiness.getALLBooks(null, null)).thenReturn(Observable.create((Observable.OnSubscribe<ArrayList<Book>>) sub -> {
            sub.onCompleted();
            verify(allBooksView, times(1)).hideProgress();
        }));
        allBooksPresenter.loadAllBooks(null, true, null);
    }
}