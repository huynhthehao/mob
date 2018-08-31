package vn.homecredit.hcvn.ui.contract.summaryContract;

import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.mock.MasterContractRespBuilder;
import vn.homecredit.hcvn.mock.MockData;
import vn.homecredit.hcvn.ui.base.TestBaseViewModel;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SummaryContractViewModelTest extends TestBaseViewModel<SummaryContractViewModel> {

    @Mock
    ContractRepository contractRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    PreferencesHelper preferencesHelper;
    @Mock
    FingerPrintHelper fingerPrintHelper;
    private String contractsId;

    @Override
    public void initData() {
        viewModel = new SummaryContractViewModel(null, contractRepository, accountRepository, fingerPrintHelper, preferencesHelper );
        contractsId = "123455";
    }

    @Test
    public void testShowSignButton(){
        MasterContractResp masterContractResp = MockData.masterContractResp();
        masterContractResp.getMasterContract().setMaterialPrepared(true);
        when(contractRepository.masterContract(contractsId))
                .thenReturn(Single.just(masterContractResp));
        viewModel.setContractsId(contractsId);
        assertEquals(R.string.master_contract_approved, ((int) viewModel.getBtnNextText().get()));
    }

    @Test
    public void testShowLoadContractsButton(){
        MasterContractResp masterContractResp = MockData.masterContractResp();
        masterContractResp.getMasterContract().setMaterialPrepared(false);
        when(contractRepository.masterContract(contractsId))
                .thenReturn(Single.just(masterContractResp));
        viewModel.setContractsId(contractsId);
        assertEquals(R.string.master_contract_prepare_data, ((int) viewModel.getBtnNextText().get()));
    }

    @Test
    public void testNextClickedAccountExpired_Finger(){
        MasterContractResp masterContractResp = MockData.masterContractResp();
        masterContractResp.getMasterContract().setMaterialPrepared(false);
        when(accountRepository.isExpired()).thenReturn(true);
        when(fingerPrintHelper.getFingerPrintAuthValue()).thenReturn(FingerPrintAuthValue.SUPPORT_AND_ENABLED);
        when(preferencesHelper.getFingerPrintSetting()).thenReturn(true);

        when(contractRepository.masterContract(contractsId))
                .thenReturn(Single.just(masterContractResp));
        viewModel.setContractsId(contractsId);

        viewModel.onNextClicked();
        assertEquals(true, viewModel.getModelShowFingerprintDialog().getValue());
        assertNotEquals(true, viewModel.getModelShowPasswordDialog().getValue());
    }

    @Test
    public void testNextClickedAccountExpired_Login(){
        MasterContractResp masterContractResp = MockData.masterContractResp();
        masterContractResp.getMasterContract().setMaterialPrepared(false);
        when(contractRepository.masterContract(contractsId))
                .thenReturn(Single.just(masterContractResp));
        when(accountRepository.isExpired()).thenReturn(true);

        //Device Not Support FingerPrint
        when(fingerPrintHelper.getFingerPrintAuthValue()).thenReturn(FingerPrintAuthValue.NOT_SUPPORT);
        when(preferencesHelper.getFingerPrintSetting()).thenReturn(true);
        viewModel.setContractsId(contractsId);
        viewModel.onNextClicked();
        assertNotEquals(true, viewModel.getModelShowFingerprintDialog().getValue());
        assertEquals(true, viewModel.getModelShowPasswordDialog().getValue());

        // Device Support FingerPrint
        // User turn off
        when(fingerPrintHelper.getFingerPrintAuthValue()).thenReturn(FingerPrintAuthValue.SUPPORT_AND_ENABLED);
        when(preferencesHelper.getFingerPrintSetting()).thenReturn(false);
        viewModel.setContractsId(contractsId);
        viewModel.onNextClicked();
        assertNotEquals(true, viewModel.getModelShowFingerprintDialog().getValue());
        assertEquals(true, viewModel.getModelShowPasswordDialog().getValue());
    }

    @Test
    public void testShowMasterContractDoc(){
        MasterContractResp masterContractResp = MasterContractRespBuilder
                .create(contractsId)
                .prepared(true)
                .build();
        when(contractRepository.masterContract(contractsId))
                .thenReturn(Single.just(masterContractResp));
        when(accountRepository.isExpired()).thenReturn(false);
        viewModel.setContractsId(contractsId);
        viewModel.onNextClicked();
        assertEquals(true, viewModel.getModelViewDoc().getValue());
    }

    @Test
    public void testPrepareSuccess() {

        MasterContractResp masterContractResp = MasterContractRespBuilder
                .create(contractsId)
                .build();

        MasterContractResp masterContractRespPrepared = MasterContractRespBuilder
                .create(contractsId)
                .prepared(true)
                .build();

        when(contractRepository.masterContract(contractsId)) .thenReturn(Single.just(masterContractResp));
        when(accountRepository.isExpired()).thenReturn(false);
        when(contractRepository.startPrepare(contractsId)).thenReturn(Observable.just(masterContractRespPrepared));

        viewModel.setContractsId(contractsId);
        viewModel.onNextClicked();

        assertEquals(R.string.master_contract_approved, ((int) viewModel.getBtnNextText().get()));
        assertEquals(true, viewModel.getModelViewDoc().getValue());

    }

    @Test
    public void testPrepareFailed() {
        MasterContractResp masterContractResp = MasterContractRespBuilder
                .create(contractsId)
                .build();

        when(contractRepository.masterContract(contractsId))
                .thenReturn(Single.just(masterContractResp));
        when(accountRepository.isExpired())
                .thenReturn(false);
        when(contractRepository.startPrepare(contractsId))
                .thenReturn(Observable.error(new HcApiException(500, "internal error")));
        viewModel.setContractsId(contractsId);
        viewModel.onNextClicked();

        assertEquals(R.string.master_contract_prepare_data, ((int) viewModel.getBtnNextText().get()));
        assertNotEquals(true, viewModel.getModelViewDoc().getValue());
        assertMessage(R.string.master_contract_approve_retry);

    }
}