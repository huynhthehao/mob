package vn.homecredit.hcvn.ui.payment.momo.whichContract;

import android.arch.lifecycle.MutableLiveData;

import org.junit.Test;

import vn.homecredit.hcvn.ui.base.TestBaseViewModel;

import static org.junit.Assert.*;

public class WhichContractViewModelTest extends TestBaseViewModel<WhichContractViewModel> {

    private MutableLiveData<Boolean> modelMyContract;
    private MutableLiveData<Boolean> modelPayForOther;

    @Override
    public void initData() {
        viewModel = new WhichContractViewModel(null);
        modelPayForOther = viewModel.getModelPayForOther();
        modelMyContract = viewModel.getModelMyContract();
    }

    @Test
    public void testMyContractClicked() {
        viewModel.onMyContractClicked();
        assertEquals(Boolean.TRUE, modelMyContract.getValue());
    }

    @Test
    public void testPayForOtherClicked() {
        viewModel.onPayForOtherClicked();
        assertEquals(Boolean.TRUE, modelPayForOther.getValue());
    }
}