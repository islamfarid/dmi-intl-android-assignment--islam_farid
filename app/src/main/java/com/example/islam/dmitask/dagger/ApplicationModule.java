package com.example.islam.dmitask.dagger;

import com.example.islam.dmitask.allbooks.bussiness.AllBooksBussiness;
import com.example.islam.dmitask.allbooks.presenter.AllBooksPresenter;
import com.example.islam.dmitask.application.DMITaskApplication;
import com.example.islam.dmitask.bookadding.bussiness.BookAddingBussiness;
import com.example.islam.dmitask.bookadding.presenter.BookAddingPresenter;
import com.example.islam.dmitask.bookdetails.bussiness.BookDetailsBussiness;
import com.example.islam.dmitask.bookdetails.presenter.BookDetailsPresenter;
import com.example.islam.dmitask.db.manager.DbManager;
import com.example.islam.dmitask.db.manager.DbManagerImp;
import com.example.islam.dmitask.network.NetworkManager;
import com.example.islam.dmitask.network.ServiceGenerator;

import dagger.Module;
import dagger.Provides;

/**
 * Created by islam on 8/19/16.
 */
@Module
public class ApplicationModule {
    //if any provided component needs the context like Database or sharedpreference
    //we will use this
    DMITaskApplication dmiTaskApplication;

    public ApplicationModule(DMITaskApplication dmiTaskApplication) {
        this.dmiTaskApplication = dmiTaskApplication;
    }

    @Provides
    public AllBooksPresenter provideAllBooksPresenter() {
        return new AllBooksPresenter();
    }

    @Provides
    public AllBooksBussiness provideAllBooksBussiness() {
        return new AllBooksBussiness();
    }

    /**
     * user name and password are static as there exist no login page but if there exist Login page
     * i will make USER Object which will be singleton and send the username and password through
     * this method to inject the network manager
     *
     * @return NetworkManager
     */
    @Provides
    public NetworkManager provideNetworkManager() {
        return ServiceGenerator.createService(NetworkManager.class, "usertest", "secret");
    }

    @Provides
    public DbManager provideDbManager() {
        return new DbManagerImp(dmiTaskApplication.getApplicationContext());
    }

    @Provides
    public BookAddingPresenter provideBookAddingPresenter() {
        return new BookAddingPresenter();
    }

    @Provides
    public BookAddingBussiness provideBookAddingBussiness() {
        return new BookAddingBussiness();
    }

    @Provides
    public BookDetailsPresenter provideBookDetailsPresenter() {
        return new BookDetailsPresenter();
    }

    @Provides
    public BookDetailsBussiness provideBookDetailsBussiness() {
        return new BookDetailsBussiness();
    }
}
