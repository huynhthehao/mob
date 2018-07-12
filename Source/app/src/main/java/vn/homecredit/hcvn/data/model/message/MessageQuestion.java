package vn.homecredit.hcvn.data.model.message;

import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.message.base.BaseMessage;
import vn.homecredit.hcvn.data.model.message.base.MessageType;
import vn.homecredit.hcvn.service.ResourceService;

public class MessageQuestion extends BaseMessage<Boolean> {

    private String mMessage;
    private String mPositive;
    private String mNegative;

    @Inject
    ResourceService resourceService;

    public MessageQuestion(String title, String message, Consumer<Boolean> onCompleted) {
        super(title, message, onCompleted);
        mMessage = message;
        mPositive = resourceService.getStringById(R.string.ok);
        mNegative = resourceService.getStringById(R.string.cancel);
    }

    public String getPositive() {
        return mPositive;
    }

    public void setNegative(String negative) {
        mNegative = negative;
    }

    @Override
    public void process(Context context) {
        new MaterialDialog.Builder(context)
                .title(this.getTitle())
                .content(this.getMessage())
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .canceledOnTouchOutside(false)
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        try {
                            MessageQuestion.this.getOnCompleted().accept(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        try {
                            MessageQuestion.this.getOnCompleted().accept(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            MessageQuestion.this.getOnCompleted().accept(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .show();
    }

    public String getMessage() {
        return mMessage;
    }
}
