package vn.homecredit.hcvn.data.model.message.base;

import android.content.Context;

import io.reactivex.functions.Consumer;


public abstract class BaseMessage<T> {
    private String mTitle;
    private String mContent;
    private Consumer<T> mOnCompleted;

    public String getTitle() {
        return mTitle;
    }
    public Consumer<T> getOnCompleted() {
        return mOnCompleted;
    }

    public BaseMessage(String title, String content, Consumer<T> onCompleted) {
        mTitle = title;
        mContent = content;
        mOnCompleted = onCompleted;
    }

    public abstract void process(Context context);

    public String getContent() {
        return mContent;
    }
}
