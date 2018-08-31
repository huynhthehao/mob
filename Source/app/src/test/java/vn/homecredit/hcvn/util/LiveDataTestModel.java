package vn.homecredit.hcvn.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LiveDataTestModel<T> implements Observer<T> {
    private final LiveData liveData;
    public List<T> observedValues = new ArrayList<>();

    public LiveDataTestModel(LiveData<T> liveData) {

        this.liveData = liveData;
    }

    @Override
    public void onChanged(@Nullable T t) {
        observedValues.add(t);
    }

    public void testObserver() {
        liveData.observeForever(this);
    }
}
