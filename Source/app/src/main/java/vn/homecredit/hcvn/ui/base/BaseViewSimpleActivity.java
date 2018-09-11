package vn.homecredit.hcvn.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseViewSimpleActivity extends BaseSimpleActivity {
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            enableNavigationClicked();
        }
    }

}
