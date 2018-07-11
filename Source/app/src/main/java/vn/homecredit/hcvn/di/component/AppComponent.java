/*
 * AppComponent.java
 *
 * Created by quan.pham
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 10:42 AM
 */

package vn.homecredit.hcvn.di.component;
import android.app.Application;


import vn.homecredit.hcvn.HCVNApp;
//import com.mindorks.framework.mvvm.di.builder.ActivityBuilder;
import vn.homecredit.hcvn.di.builder.ActivityBuilder;
import vn.homecredit.hcvn.di.builder.FragmentBuilderModule;
import vn.homecredit.hcvn.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AppModule.class,
        FragmentBuilderModule.class,
        ActivityBuilder.class})
public interface AppComponent {
    void inject(HCVNApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
