package vn.homecredit.hcvn.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import vn.homecredit.hcvn.database.dao.NotificationDao;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;

@Database(entities = {NotificationModel.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    // --- DAO ---
    public abstract NotificationDao getNotificationDao();
}
