package vn.homecredit.hcvn.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.database.AppDatabase;
import vn.homecredit.hcvn.database.dao.NotificationDao;

@Module
public class RoomModule {
    private AppDatabase appDatabase;

    public RoomModule(Application mApplication) {
        appDatabase = Room.databaseBuilder(mApplication, AppDatabase.class, "hc-db").build();
    }

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    NotificationDao providesNotificationDao(AppDatabase appDatabase) {
        return appDatabase.getNotificationDao();
    }
}
