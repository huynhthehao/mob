package vn.homecredit.hcvn.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.AppDataManager;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.acl.AclDataManagerImpl;
import vn.homecredit.hcvn.data.acl.AclDatabaseService;
import vn.homecredit.hcvn.data.acl.AclDatabaseServiceImpl;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.remote.RestServiceImpl;
import vn.homecredit.hcvn.data.remote.acl.AclRestService;
import vn.homecredit.hcvn.data.remote.acl.AclRestServiceImpl;
import vn.homecredit.hcvn.data.remote.clwmap.ClwMapService;
import vn.homecredit.hcvn.data.remote.clwmap.ClwMapServiceImpl;
import vn.homecredit.hcvn.data.remote.disbursement.DisbursementService;
import vn.homecredit.hcvn.data.remote.disbursement.DisbursementServiceImpl;
import vn.homecredit.hcvn.data.remote.payment.PaymentService;
import vn.homecredit.hcvn.data.remote.payment.PaymentServiceImpl;
import vn.homecredit.hcvn.data.remote.payoo.PayooRestService;
import vn.homecredit.hcvn.data.remote.payoo.PayooRestServiceImpl;
import vn.homecredit.hcvn.data.remote.pos.PosRestService;
import vn.homecredit.hcvn.data.remote.pos.PosRestServiceImpl;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.data.repository.AccountRepositoryImpl;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.data.repository.ContractRepositoryImpl;
import vn.homecredit.hcvn.data.repository.MapRepository;
import vn.homecredit.hcvn.data.repository.MapRepositoryImpl;
import vn.homecredit.hcvn.data.repository.NotificationRepository;
import vn.homecredit.hcvn.data.repository.NotificationRepositoryImpl;
import vn.homecredit.hcvn.data.repository.SupportRepository;
import vn.homecredit.hcvn.data.repository.SupportRepositoryImpl;
import vn.homecredit.hcvn.di.PreferenceInfo;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelperImpl;
import vn.homecredit.hcvn.helpers.memory.MemoryHelper;
import vn.homecredit.hcvn.helpers.memory.MemoryHelperImpl;
import vn.homecredit.hcvn.helpers.prefs.AppPreferencesHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.rules.acl.AclRuleFactory;
import vn.homecredit.hcvn.rules.acl.AclRuleFactoryImpl;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.service.DeviceInfoImpl;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.service.OneSignalServiceImpl;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.service.ResourceServiceImpl;
import vn.homecredit.hcvn.service.VersionService;
import vn.homecredit.hcvn.service.VersionServiceImpl;
import vn.homecredit.hcvn.utils.AppConstants;
import vn.homecredit.hcvn.utils.imageLoader.ImageLoader;
import vn.homecredit.hcvn.utils.rx.AppSchedulerProvider;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class AppModule {

    @Provides
    @Singleton
    RestService proviRestService(RestServiceImpl restService) {
        return restService;
    }

    @Provides
    @Singleton
    AclRestService provideAclRestService(AclRestServiceImpl aclRestService) {
        return aclRestService;
    }

    @Provides
    @Singleton
    PayooRestService providePayooRestService(PayooRestServiceImpl payooRestService) {
        return payooRestService;
    }

    @Provides
    @Singleton
    PosRestService providePosRestService(PosRestServiceImpl posRestService) {
        return posRestService;
    }

    @Provides
    @Singleton
    ClwMapService provideClwMapServiceImpl(ClwMapServiceImpl clwMapService) {
        return clwMapService;
    }

    @Provides
    @Singleton
    DisbursementService provideDisbusermentServiceImpl(DisbursementServiceImpl disbursementService) {
        return disbursementService;
    }

    @Provides
    @Singleton
    PaymentService providePaymentService(PaymentServiceImpl paymentService) {
        return paymentService;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @Singleton
    MemoryHelper provideMemoryHelper(MemoryHelperImpl memoryHelper) {
        return memoryHelper;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    ApiHeader.AclApiHeader provACLApiHeader(AclDatabaseService aclDatabaseService) {
        return new ApiHeader.AclApiHeader(
                aclDatabaseService.getAclAccessToken());
    }

    @Provides
    @Singleton
    ApiHeader.PayooApiHeader provPayooApiHeader() {
        return new ApiHeader.PayooApiHeader("YM_7fPw6xA", "Partner07");
    }

    @Provides
    @Singleton
    ApiHeader.PosApiHeader provPosApiHeader() {
        return new ApiHeader.PosApiHeader();
    }

    @Provides
    @Singleton
    ApiHeader.ClwMapApiHeader provClwMapApiHeader() {
        return new ApiHeader.ClwMapApiHeader();
    }

    @Provides
    @Singleton
    ApiHeader.DisbursementApiHeader provDisbursementApiHeader() {
        return new ApiHeader.DisbursementApiHeader();
    }

    @Provides
    @Singleton
    ApiHeader.PaymentApiHeader provPaymentApiHeader() {
        return new ApiHeader.PaymentApiHeader();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    OneSignalService provideOneSignalService(OneSignalServiceImpl oneSignalService) {
        return oneSignalService;
    }

    @Provides
    @Singleton
    AclRuleFactory provideAclRuleFactory(AclRuleFactoryImpl aclRuleFactory) {
        return aclRuleFactory;
    }

    @Provides
    @Singleton
    DeviceInfo provideDeviceInfo(DeviceInfoImpl deviceInfo) {
        return deviceInfo;
    }

    @Provides
    @Singleton
    VersionService provideVersionService(VersionServiceImpl versionService) {
        return versionService;
    }

    @Provides
    @Singleton
    ResourceService provideResourceService(ResourceServiceImpl resourceService) {
        return resourceService;
    }

    @Provides
    @Singleton
    AclDatabaseService provideAclDatabaseService(AclDatabaseServiceImpl aclDatabaseService) {
        return aclDatabaseService;
    }

    @Provides
    @Singleton
    AclDataManager provideAclDataManager(AclDataManagerImpl aclDataManager) {
        return aclDataManager;
    }

    @Provides
    @Singleton
    FingerPrintHelper provideFingerPrintHelper(FingerPrintHelperImpl fingerPrintHelperImpl) {
        return fingerPrintHelperImpl;
    }

    @Provides
    @Singleton
    AccountRepository provideAccountRepository(AccountRepositoryImpl accountRepositoryImpl) {
        return accountRepositoryImpl;
    }

    @Provides
    @Singleton
    NotificationRepository provideNotificationRepository(NotificationRepositoryImpl notificationRepositoryImpl) {
        return notificationRepositoryImpl;
    }

    @Provides
    @Singleton
    MapRepository provideMapRepository(MapRepositoryImpl mapRepository) {
        return mapRepository;
    }


    @Provides
    @Singleton
    ContractRepository provideContractRepository(ContractRepositoryImpl contractRepositoryImpl) {
        return contractRepositoryImpl;
    }

    @Provides
    @Singleton
    SupportRepository provideSupportRepository(SupportRepositoryImpl supportRepositoryImpl) {
        return supportRepositoryImpl;
    }
}