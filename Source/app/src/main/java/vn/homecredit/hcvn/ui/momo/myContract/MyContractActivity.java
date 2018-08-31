package vn.homecredit.hcvn.ui.momo.myContract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.contract.main.ContractFragment;

public class MyContractActivity extends AppCompatActivity {
    private static final String TAG_MY_CONTRACTS = "TAG_MY_CONTRACTS";

    public static void start(Context context) {
        Intent intent = new Intent(context, MyContractActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contract);
        if (getSupportFragmentManager().findFragmentByTag(TAG_MY_CONTRACTS) == null) {
            ContractFragment contractFragment = ContractFragment.newInstance(true);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, contractFragment, TAG_MY_CONTRACTS)
                    .commit();
        }
    }
}
