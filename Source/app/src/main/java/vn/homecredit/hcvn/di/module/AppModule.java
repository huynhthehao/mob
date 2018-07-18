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
import vn.homecredit.hcvn.data.local.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.data.local.fingerprint.FingerPrintHelperImpl;
import vn.homecredit.hcvn.data.local.memory.MemoryHelper;
import vn.homecredit.hcvn.data.local.memory.MemoryHelperImpl;
import vn.homecredit.hcvn.data.local.prefs.AppPreferencesHelper;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.remote.RestServiceImpl;
import vn.homecredit.hcvn.data.remote.acl.AclRestService;
import vn.homecredit.hcvn.data.remote.acl.AclRestServiceImpl;
import vn.homecredit.hcvn.di.PreferenceInfo;
import vn.homecredit.hcvn.rules.acl.AclRuleFactory;
import vn.homecredit.hcvn.rules.acl.AclRuleFactoryImpl;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.service.DeviceInfoImpl;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.service.OneSignalServiceImpl;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.service.ResourceServiceImpl;
import vn.homecredit.hcvn.service.ProfileService;
import vn.homecredit.hcvn.service.ProfileServiceImpl;
import vn.homecredit.hcvn.service.VersionService;
import vn.homecredit.hcvn.service.VersionServiceImpl;
import vn.homecredit.hcvn.utils.AppConstants;
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
    ProfileService provideProfileService(ProfileServiceImpl profileService) {
        return profileService;
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
}