package vn.homecredit.hcvn.ui.support;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.homecredit.hcvn.R;

public class SupportDoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback_done);
        findViewById(R.id.btDone).setOnClickListener(view -> finish());
    }
}
