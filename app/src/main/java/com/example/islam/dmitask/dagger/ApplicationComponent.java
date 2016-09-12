package com.example.islam.dmitask.dagger;


import com.example.islam.dmitask.allbooks.bussiness.AllBooksBussiness;
import com.example.islam.dmitask.allbooks.presenter.AllBooksPresenter;
import com.example.islam.dmitask.allbooks.view.AllBooksFragment;
import com.example.islam.dmitask.bookadding.bussiness.BookAddingBussiness;
import com.example.islam.dmitask.bookadding.presenter.BookAddingPresenter;
import com.example.islam.dmitask.bookadding.view.BookAddingFragment;
import com.example.islam.dmitask.bookdetails.bussiness.BookDetailsBussiness;
import com.example.islam.dmitask.bookdetails.presenter.BookDetailsPresenter;
import com.example.islam.dmitask.bookdetails.view.BookDetailsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by islam on 8/19/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(AllBooksFragment allBooksFragment);

    void inject(AllBooksPresenter allBooksPresenter);

    void inject(AllBooksBussiness allBooksBussiness);

    void inject(BookAddingBussiness bookAddingBussiness);

    void inject(BookAddingFragment bookAddingFragment);

    void inject(BookAddingPresenter bookAddingPresenter);

    void inject(BookDetailsBussiness bookDetailsBussiness);

    void inject(BookDetailsFragment bookDetailsFragment);

    void inject(BookDetailsPresenter bookDetailsPresenter);
}
