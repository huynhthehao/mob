package vn.homecredit.hcvn.ui.momo.paymentMomo;

import android.databinding.ObservableField;

import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.momo.RePaymentResp;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.TestBaseViewModel;
import vn.homecredit.hcvn.utils.TestData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PaymentMomoViewModelTest extends TestBaseViewModel<PaymentMomoViewModel>{
    @Mock ContractRepository contractRepository;
    private ObservableField<Boolean> bindVisibleContract;
    private ObservableField<Boolean> bindVisibleCustomerId;
    private ObservableField<Boolean> bindVisibleFullname;
    private ObservableField<Boolean> bindVisibleDuedate;

    @Override
    public void initData() {
        viewModel = new PaymentMomoViewModel(contractRepository, null);
        bindVisibleContract = viewModel.getBindVisibleContract();
        bindVisibleCustomerId = viewModel.getBindVisibleCustomerId();
        bindVisibleFullname = viewModel.getBindVisibleFullname();
        bindVisibleDuedate = viewModel.getBindVisibleDuedate();
    }

    @Test
    public void testVisibleInfo() {
        RePaymentResp rePaymentResp = TestData.rePaymentResp();
        HcContract hcContract = TestData.activeContract();
        viewModel.setContract(hcContract);
        when(contractRepository.getRePayment(hcContract.getContractNumber()))
                .thenReturn(Single.just(rePaymentResp));
        viewModel.getRepayment();
        assertEquals(true, bindVisibleContract.get());
        assertEquals(true, bindVisibleCustomerId.get());
        assertEquals(true, bindVisibleDuedate.get());
        assertEquals(true, bindVisibleDuedate.get());
    }

    @Test
    public void testInvisibleInfo() {
        RePaymentResp rePaymentResp = TestData.rePaymentResp();
        rePaymentResp.getRePaymentData().setIdNumber(null);
        rePaymentResp.getRePaymentData().setContractNumber(null);
        rePaymentResp.getRePaymentData().setDueDate(null);
        rePaymentResp.getRePaymentData().setFullName(null);
        HcContract hcContract = TestData.activeContract();
        viewModel.setContract(hcContract);
        when(contractRepository.getRePayment(hcContract.getContractNumber()))
                .thenReturn(Single.just(rePaymentResp));
        viewModel.getRepayment();
        assertEquals(false, bindVisibleContract.get());
        assertEquals(false, bindVisibleCustomerId.get());
        assertEquals(false, bindVisibleDuedate.get());
        assertEquals(false, bindVisibleDuedate.get());
    }


}