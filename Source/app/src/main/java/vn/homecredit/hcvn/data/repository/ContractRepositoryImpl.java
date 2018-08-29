package vn.homecredit.hcvn.data.repository;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.ContractType;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyResp;
import vn.homecredit.hcvn.data.model.api.contract.PaymentHistoryResp;
import vn.homecredit.hcvn.data.model.api.contract.ScheduleDetailResp;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementModel;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementResp;
import vn.homecredit.hcvn.ui.contract.statement.statementdetails.model.StatementDetailsResp;
import vn.homecredit.hcvn.utils.TestData;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ContractRepositoryImpl implements ContractRepository {
    public static final int MASTERCONTRACT_PREPARE_TIMEOUT = 60;
    public static final int MASTERCONTRACT_PREPARE_INTERVAL = 20;

    private final RestService restService;

    @Inject
    public ContractRepositoryImpl(RestService restService ) {
        this.restService = restService;
    }

    @Override
    public Single<ContractResp> contracts() {
        return restService.contract()
                .map(contractResp -> {
                    if (contractResp == null || contractResp.getData() == null) {
                        return contractResp;
                    }
                    List<HcContract> contractMasterList = convertMasterToHcContract(contractResp.getData().getMasterContracts());
                    if (contractResp.getData().getContracts() != null && contractMasterList != null) {
                        contractResp.getData().getContracts().addAll(contractMasterList);
                    }
                    List<HcContract> contractList = groupContract(contractResp.getData().getContracts());
                    contractResp.getData().setContracts(contractList);
                    return contractResp;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ContractResp> activeContracts() {
        return restService.contract()
                .map(contractResp -> {
                    if (contractResp == null || contractResp.getData() == null) {
                        return contractResp;
                    }
                    List<HcContract> contractMasterList = convertMasterToHcContract(contractResp.getData().getMasterContracts());
                    if (contractResp.getData().getContracts() != null && contractMasterList != null) {
                        contractResp.getData().getContracts().addAll(contractMasterList);
                    }
                    List<HcContract> contractList = groupActiveContract(contractResp.getData().getContracts());
                    contractResp.getData().setContracts(contractList);
                    return contractResp;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Single<MasterContractResp> masterContract(String contractId) {
        return restService.masterContract(contractId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<MasterContractDocResp> masterContractDoc(String contractId) {
        return restService.masterContractDoc(contractId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MasterContractResp> startPrepare(String contractId) {
        int numberRequest = MASTERCONTRACT_PREPARE_TIMEOUT / MASTERCONTRACT_PREPARE_INTERVAL;
        return Observable.interval(MASTERCONTRACT_PREPARE_INTERVAL, TimeUnit.SECONDS)
                .startWith(0L)
                .flatMap(aLong -> {
                    if (aLong >= numberRequest) {
                        return Observable.error(new Throwable("Timeout"));
                    } else {
                        return restService.masterContract(contractId)
                                .toObservable();
                    }
                })
                .filter(masterContractResp -> {
                    if (masterContractResp == null || masterContractResp.getMasterContract() == null) {
                        return false;
                    }
                    return masterContractResp.getMasterContract().isMaterialPrepared();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;

    }

    @Override
    public Single<OtpTimerResp> masterContractApproved(String contractId) {
        return restService.masterContractApprove(contractId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<MasterContractVerifyResp> masterContractVerify(String contractId, String otp, boolean hasDisbursementBankAccount, boolean isCreditCardContract) {
        return restService.masterContractVerify(contractId, otp, hasDisbursementBankAccount, isCreditCardContract)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ScheduleDetailResp> viewInstalmentsv1(String contractId) {
        return restService.viewInstalmentsv1(contractId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<PaymentHistoryResp> viewPaymentsv1(String contractId) {
        return restService.viewPaymentsv1(contractId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<StatementResp> getStatements(String contractId) {
        return restService.getStatements(contractId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<StatementDetailsResp> getStatementDetails(String contractId, StatementModel statementModel) {
        return restService.getStatementDetails(contractId, statementModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> checkMasterContractVerified(String contractId, int timeout, int interval) {
        int numberRequest = timeout / interval;
        return Observable.interval(interval, TimeUnit.MILLISECONDS)
                .startWith(0L)
                .flatMap((Function<Long, Observable<MasterContractResp>>) aLong -> {
                    if (aLong >= numberRequest) {
                        return Observable.error(new Throwable("Timeout"));
                    } else {
                        return restService.masterContract(contractId).toObservable();
                    }
                })
                .filter(masterContractResp -> {
                    if (masterContractResp == null || masterContractResp.isSuccess() || masterContractResp.getMasterContract() == null) {
                        return false;
                    }
                    return masterContractResp.getMasterContract().isSigned();

                })
                .map(masterContractResp -> true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    private List<HcContract> groupActiveContract(List<HcContract> hcContractList) {
        if (hcContractList == null) {
            return null;
        }
        List<HcContract> activeContractList = new ArrayList<>();
        for (HcContract hcContract : hcContractList) {
            if (hcContract.getTypeStatus() == HcContract.STATUS_ACTIVE) {
                if (activeContractList.size() == 0) {
                    hcContract.setShowSection(true);
                }
                activeContractList.add(hcContract);
            }
        }
        List<HcContract> contractList = new ArrayList<>();
        contractList.addAll(activeContractList);
        return contractList;
    }

    @NonNull
    private List<HcContract> groupContract(List<HcContract> hcContractList) {
        if (hcContractList == null) {
            return null;
        }
        List<HcContract> activeContractList = new ArrayList<>();
        List<HcContract> pendingContractList = new ArrayList<>();
        List<HcContract> closeContractList = new ArrayList<>();
        for (HcContract hcContract : hcContractList) {
            if (hcContract.getTypeStatus() == HcContract.STATUS_ACTIVE) {
                if (activeContractList.size() == 0) {
                    hcContract.setShowSection(true);
                }
                activeContractList.add(hcContract);
            } else if (hcContract.getTypeStatus() == HcContract.STATUS_PENDING) {
                if (pendingContractList.size() == 0) {
                    hcContract.setShowSection(true);
                }
                pendingContractList.add(hcContract);
            } else {
                if (closeContractList.size() == 0) {
                    hcContract.setShowSection(true);
                }
                closeContractList.add(hcContract);
            }
        }
        List<HcContract> contractList = new ArrayList<>();
        contractList.addAll(activeContractList);
        contractList.addAll(pendingContractList);
        contractList.addAll(closeContractList);
        return contractList;
    }

    @NonNull
    private List<HcContract> convertMasterToHcContract(List<MasterContract> masterContractList) {
        List<HcContract> contractMasterList = new ArrayList<>();
        if (masterContractList != null) {
            for (MasterContract masterContract : masterContractList) {
                HcContract hcContract = new HcContract();
                hcContract.setContractNumber(masterContract.getContractNumber());
                hcContract.setMasterContract(masterContract);
                contractMasterList.add(hcContract);
            }
        }
        return contractMasterList;
    }
}
