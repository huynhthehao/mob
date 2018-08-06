/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 10/07/18 1:50 PM, by An.NguyenN1
 */

package vn.homecredit.hcvn.ui.more;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.ServerSettings;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class MoreViewModel extends BaseViewModel {

    private VersionResp.VersionRespData versionRespData;
    private MutableLiveData<Boolean> modelProfile = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelPassword = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelSetting = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelLocation = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelLogout = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelMomo = new MutableLiveData<>();
    private MutableLiveData<String> modelUrlTerm = new MutableLiveData<>();
    private MutableLiveData<String> modelUrlUserGuide = new MutableLiveData<>();
    private ObservableField<String> usernameField = new ObservableField<>("");
    private ObservableField<String> phoneField = new ObservableField<>("");
    private final DataManager dataManager;

    @Inject
    public MoreViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.dataManager = dataManager;
    }

    public ObservableField<String> getPhoneField() {
        return phoneField;
    }

    public ObservableField<String> getUsernameField() {
        return usernameField;
    }

    public MutableLiveData<Boolean> getModelLocation() {
        return modelLocation;
    }

    public MutableLiveData<Boolean> getModelLogout() {
        return modelLogout;
    }

    public MutableLiveData<Boolean> getModelPassword() {
        return modelPassword;
    }

    public MutableLiveData<Boolean> getModelProfile() {
        return modelProfile;
    }

    public MutableLiveData<Boolean> getModelSetting() {
        return modelSetting;
    }

    public MutableLiveData<String> getModelUrlTerm() {
        return modelUrlTerm;
    }

    public MutableLiveData<Boolean> getModelMomo() {
        return modelMomo;
    }

    public MutableLiveData<String> getModelUrlUserGuide() {
        return modelUrlUserGuide;
    }

    public void init() {
        versionRespData = dataManager.getVersionRespData();
        ProfileResp.ProfileRespData profileRespData = dataManager.getProfileRespData();
        if (profileRespData != null) {
            usernameField.set(profileRespData.getFullName());
            phoneField.set(profileRespData.getPhoneNumber());
        }

        modelProfile.setValue(false);
        modelPassword.setValue(false);
        modelSetting.setValue(false);
        modelLocation.setValue(false);
        modelMomo.setValue(false);
        modelUrlTerm.setValue(null);
        modelUrlUserGuide.setValue(null);
    }

    public String getUrlTerm() {
        if (versionRespData == null) {
            return null;
        }
        ServerSettings serverSettings = versionRespData.getSettings();
        if (serverSettings == null) {
            return null;
        }
        return serverSettings.getTermsAndConditionsForEServicesUrl();
    }

    public String getUrlUserGuide() {
        if (versionRespData == null) {
            return null;
        }
        ServerSettings serverSettings = versionRespData.getSettings();
        if (serverSettings == null) {
            return null;
        }
        return serverSettings.getGuidanceForInternetBankingServiceUrl();
    }

    public void logout() {
        dataManager.logout();
    }

    public void clickedProfile() {
        modelProfile.setValue(true);
    }
    public void clickedPassword() {
        modelPassword.setValue(true);
    }
    public void clickedSetting() {
        modelSetting.setValue(true);
    }
    public void clickedLocation() {
        modelLocation.setValue(true);
    }
    public void clickedMomo() {
        modelMomo.setValue(true);
    }
    public void clickedTerm() {
        modelUrlTerm.setValue(getUrlTerm());
    }

    public void clickedUserGuide() {
        modelUrlUserGuide.setValue(getUrlUserGuide());
    }
    public void clickedLogout() {
        modelLogout.setValue(true);
    }
}
