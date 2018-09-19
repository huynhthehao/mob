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
import vn.homecredit.hcvn.service.tracking.FBAnalyticsServiceImpl;
import vn.homecredit.hcvn.service.tracking.GAAnalyticsServiceImpl;
import vn.homecredit.hcvn.service.tracking.TrackingService;
import vn.homecredit.hcvn.ui.notification.manager.NotificationManager;
import vn.homecredit.hcvn.ui.notification.manager.NotificationManagerImpl;
import vn.homecredit.hcvn.utils.AppConstants;
import vn.homecredit.hcvn.utils.rx.AppSchedulerProvider;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class AppModule {

    @Provides
    @Singleton
    public RestService proviRestService(RestServiceImpl restService) {
        return restService;
    }

    @Provides
    @Singleton
    public AclRestService provideAclRestService(AclRestServiceImpl aclRestService) {
        return aclRestService;
    }

    @Provides
    @Singleton
    public CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    public Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    public DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @Singleton
    public MemoryHelper provideMemoryHelper(MemoryHelperImpl memoryHelper) {
        return memoryHelper;
    }

    @Provides
    @PreferenceInfo
    public String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    public PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    public ApiHeader.ProtectedApiHeader provideProtectedApiHeader(PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    public ApiHeader.AclApiHeader provACLApiHeader(AclDatabaseService aclDatabaseService) {
        return new ApiHeader.AclApiHeader(
                aclDatabaseService.getAclAccessToken());
    }

    @Provides
    public SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    public OneSignalService provideOneSignalService(OneSignalServiceImpl oneSignalService) {
        return oneSignalService;
    }

    @Provides
    @Singleton
    public AclRuleFactory provideAclRuleFactory(AclRuleFactoryImpl aclRuleFactory) {
        return aclRuleFactory;
    }

    @Provides
    @Singleton
    public DeviceInfo provideDeviceInfo(DeviceInfoImpl deviceInfo) {
        return deviceInfo;
    }

    @Provides
    @Singleton
    public VersionService provideVersionService(VersionServiceImpl versionService) {
        return versionService;
    }

    @Provides
    @Singleton
    public ResourceService provideResourceService(ResourceServiceImpl resourceService) {
        return resourceService;
    }

    @Provides
    @Singleton
    public AclDatabaseService provideAclDatabaseService(AclDatabaseServiceImpl aclDatabaseService) {
        return aclDatabaseService;
    }

    @Provides
    @Singleton
    public AclDataManager provideAclDataManager(AclDataManagerImpl aclDataManager) {
        return aclDataManager;
    }

    @Provides
    @Singleton
    public FingerPrintHelper provideFingerPrintHelper(FingerPrintHelperImpl fingerPrintHelperImpl) {
        return fingerPrintHelperImpl;
    }

    @Provides
    @Singleton
    public AccountRepository provideAccountRepository(AccountRepositoryImpl accountRepositoryImpl) {
        return accountRepositoryImpl;
    }

    @Provides
    @Singleton
    public NotificationRepository provideNotificationRepository(NotificationRepositoryImpl notificationRepositoryImpl) {
        return notificationRepositoryImpl;
    }

    @Provides
    @Singleton
    public MapRepository provideMapRepository(MapRepositoryImpl mapRepository) {
        return mapRepository;
    }


    @Provides
    @Singleton
    public ContractRepository provideContractRepository(ContractRepositoryImpl contractRepositoryImpl) {
        return contractRepositoryImpl;
    }

    @Provides
    @Singleton
    public SupportRepository provideSupportRepository(SupportRepositoryImpl supportRepositoryImpl) {
        return supportRepositoryImpl;
    }

    @Provides
    @Singleton
    public TrackingService provideTrackService(Context context, GAAnalyticsServiceImpl gaTrackService, FBAnalyticsServiceImpl fbTrackService) {
        return new TrackingService(context, gaTrackService, fbTrackService);
    }

    @Provides
    @Singleton
    ApiHeader.PayooApiHeader provPayooApiHeader() {
        return new ApiHeader.PayooApiHeader("YM_7fPw6xA", "Partner07");
    }

    @Provides
    @Singleton
    public NotificationManager provideNotificationManager(NotificationManagerImpl notificationManagerImpl) {
        return notificationManagerImpl;
    }

}