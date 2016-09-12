package com.example.islam.dmitask.application;

import android.app.Application;

import com.example.islam.dmitask.dagger.ApplicationComponent;
import com.example.islam.dmitask.dagger.ApplicationModule;
import com.example.islam.dmitask.dagger.DaggerApplicationComponent;

/**
 * Created by islam on 10/09/16.
 */
public class DMITaskApplication extends Application {
    private static DMITaskApplication dmiTaskApplication;
    ApplicationComponent applicationComponent;

    /**
     * we are sure that this instance wont be null in our application
     * as we give it a value in the application onCreate.
     *
     * @return
     */
    public static DMITaskApplication getInstance() {
        return dmiTaskApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.dmiTaskApplication = this;
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
