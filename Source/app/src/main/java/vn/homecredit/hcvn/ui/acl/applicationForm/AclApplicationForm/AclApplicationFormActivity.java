/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/9/18 4:55 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclAfSelectLoan.AclAfSelectLoanFragment;

public class AclApplicationFormActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, AclApplicationFormActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acl_application_form);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentFrame, new AclAfSelectLoanFragment())
                    .commit();
        }
    }
}
