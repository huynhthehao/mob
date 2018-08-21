package vn.homecredit.hcvn.util;


import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import vn.homecredit.hcvn.HCVNApp;
import vn.homecredit.hcvn.di.component.AppComponent;
import vn.homecredit.hcvn.di.module.AppModule;
import vn.homecredit.hcvn.di.module.RoomModule;

public class RobolectricMockTestRule extends DaggerMockRule<AppComponent> {


    public RobolectricMockTestRule() {
        super(AppComponent.class );

        customizeBuilder(new BuilderCustomizer<AppComponent.Builder>() {
            @Override
            public AppComponent.Builder customize(AppComponent.Builder builder) {
                return builder.application(getApplication()).roomModule(new RoomModule(getApplication()));
            }
        });

        set(new ComponentSetter<AppComponent>() {
            @Override
            public void setComponent(AppComponent component) {
                component.inject(getApplication());
            }
        });
    }

    private static HCVNApp getApplication() {
        return ((HCVNApp) RuntimeEnvironment.application);
    }
}