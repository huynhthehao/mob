package vn.homecredit.hcvn.ui.profile;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ProfileViewModel extends BaseViewModel {
    private final ResourceService resourceService;
    private ObservableField<SpannableString> profileUpdateMessage = new ObservableField<>();
    private MutableLiveData<String> modelCustomerServiceCall = new MutableLiveData<>();
    String customerServicePhone = "";

    @Inject
    public ProfileViewModel(SchedulerProvider schedulerProvider, ResourceService resourceService) {
        super(schedulerProvider);
        this.resourceService = resourceService;
    }

    @Override
    public void init() {
        customerServicePhone = resourceService.getStringById(R.string.customer_service_phone);
        String profileMessage = resourceService.getStringById(R.string.update_profile_detail_message) + " " + customerServicePhone;
        final SpannableString spannableString = new SpannableString(profileMessage);
        spannableString.setSpan(clickableSpan, profileMessage.length() - customerServicePhone.length() - 1,
                profileMessage.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        profileUpdateMessage.set(spannableString);
    }

    public ObservableField<SpannableString> getProfileUpdateMessage() {
        return profileUpdateMessage;
    }

    final ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(final View view) {
            modelCustomerServiceCall.setValue(customerServicePhone);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(resourceService.getColorById(R.color.primary_red));
            ds.setUnderlineText(false);
            ds.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    };

    public MutableLiveData<String> getModelCustomerServiceCall() {
        return modelCustomerServiceCall;
    }
}
