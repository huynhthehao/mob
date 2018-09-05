package vn.homecredit.hcvn.data.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import vn.homecredit.hcvn.data.model.api.contract.ContractDataResp;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.remote.RestServiceImpl;
import vn.homecredit.hcvn.util.ImmediateSchedulerProvider;
import vn.homecredit.hcvn.utils.TestData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ContractRepositoryImplTest {
    @Rule public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    RestServiceImpl restService;

    ContractRepositoryImpl contractRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        contractRepository = new ContractRepositoryImpl(restService, new ImmediateSchedulerProvider());
    }

    @Test
    public void testActiveContract_order() {
        HcContract activeContract1 = TestData.activeContract();
        HcContract activeContract2 = TestData.activeContract();
        List<HcContract> activeContractList = Arrays.asList(activeContract1, activeContract2);
        List<HcContract> activeContractList_reverse = Arrays.asList(activeContract2, activeContract1);
        List<HcContract> hcContractList = Arrays.asList(TestData.closeContract(),
                activeContract2,
                TestData.closeContract(),
                activeContract1 );
        ContractDataResp contractDataResp = new ContractDataResp();
        contractDataResp.setContracts(hcContractList);
        ContractResp contractResp = new ContractResp();
        contractResp.setResponseCode(0);
        contractResp.setData(contractDataResp);
        when(restService.contract()).thenReturn(Single.just(contractResp));

        TestObserver<ContractResp> testObserver =  contractRepository.activeContracts().test();
        testObserver.assertNoErrors()
                .assertValue(contractResp1 -> !contractResp1.getData().getContracts().equals(activeContractList))
                .assertValue(contractResp2 -> contractResp2.getData().getContracts().equals(activeContractList_reverse))
        ;

    }
    @Test
    public void testActiveContract() {
        HcContract activeContract1 = TestData.activeContract();
        HcContract activeContract2 = TestData.activeContract();
        List<HcContract> activeContractList = Arrays.asList(activeContract1, activeContract2);
        List<HcContract> hcContractList = Arrays.asList(TestData.closeContract(),
                activeContract1,
                TestData.closeContract(),
                activeContract2);
        ContractDataResp contractDataResp = new ContractDataResp();
        contractDataResp.setContracts(hcContractList);
        ContractResp contractResp = new ContractResp();
        contractResp.setResponseCode(0);
        contractResp.setData(contractDataResp);
        when(restService.contract()).thenReturn(Single.just(contractResp));

        TestObserver<ContractResp> testObserver =  contractRepository.activeContracts().test();
        testObserver.assertNoErrors()
                .assertValue(contractResp1 -> contractResp1.getData().getContracts().equals(activeContractList)) ;

    }

    @Test
    public void testActiveContract_noMastercard() {
        HcContract activeContract1 = TestData.activeContract();
        HcContract activeContract2 = TestData.activeContract();
        List<HcContract> activeContractList = Arrays.asList(activeContract1, activeContract2);
        List<HcContract> hcContractList = Arrays.asList(TestData.closeContract(),
                activeContract1,
                TestData.activeCreditCardContract(),
                TestData.closeContract(),
                activeContract2);
        ContractDataResp contractDataResp = new ContractDataResp();
        contractDataResp.setContracts(hcContractList);
        ContractResp contractResp = new ContractResp();
        contractResp.setResponseCode(0);
        contractResp.setData(contractDataResp);
        when(restService.contract()).thenReturn(Single.just(contractResp));

        TestObserver<ContractResp> testObserver =  contractRepository.activeContracts().test();
        testObserver.assertNoErrors()
                .assertValue(contractResp1 -> contractResp1.getData().getContracts().equals(activeContractList)) ;

    }






}