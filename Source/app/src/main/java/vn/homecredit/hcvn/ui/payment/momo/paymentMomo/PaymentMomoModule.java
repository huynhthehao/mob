/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 10/07/18 4:21 PM, by An.NguyenN1
 */

package vn.homecredit.hcvn.ui.payment.momo.paymentMomo;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;

@Module
public class PaymentMomoModule {
    @Provides
    ViewModelProvider.Factory paymentMomoViewModelFactory(PaymentMomoViewModel paymentMomoViewModel) {
        return new ViewModelProviderFactory<>(paymentMomoViewModel);
    }

}
