package vn.homecredit.hcvn.ui.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import vn.homecredit.hcvn.ui.notification.model.NotificationModel;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface NotificationDao {
    @Insert(onConflict = REPLACE)
    void insert(List<NotificationModel> notifications);

    @Update
    void update(NotificationModel notificationModel);

    @Query("SELECT * from notifications WHERE id = :id")
    NotificationModel load(String id);

    @Query("SELECT * FROM notifications")
    List<NotificationModel> loadNotifications();
}
