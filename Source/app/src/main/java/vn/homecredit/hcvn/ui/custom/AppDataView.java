package vn.homecredit.hcvn.ui.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.homecredit.hcvn.R;

public class AppDataView extends LinearLayout {
    ViewGroup vContent;
    View vLoadingMore;
    View vLoading;
    View vError;
    View vEmpty;
    TextView tvError;
    TextView tvEmpty;
    ImageView ivError;
    ImageView ivEmpty;
    View contentView;
    SwipeRefreshLayout swipeRefreshLayout;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isDataShown;
    private ScrollToEndListener scrollToEndListener;
    private PullToRefrestListener pullToRefrestListener;
    private TapToReloadListener tapToReloadListener;
    private String mError = "";
    boolean isPullingToRefreshSupported = false;
    boolean isLoadingMoreSupported = false;

    public AppDataView(Context context) {
        this(context, null);
    }

    public AppDataView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_app_data, this);
        initView(view);
    }

    private void initView(View view) {
        vContent = view.findViewById(R.id.vContent);
        vLoadingMore = view.findViewById(R.id.vLoadingMore);
        vLoading = view.findViewById(R.id.vLoading);
        vError = view.findViewById(R.id.vError);
        vEmpty = view.findViewById(R.id.vEmpty);
        tvError = view.findViewById(R.id.tvError);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        ivError = view.findViewById(R.id.ivError);
        ivEmpty = view.findViewById(R.id.ivEmpty);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }

    public void initContentView(View contentView, int dataDrawableId, int emptyStringId, ScrollToEndListener scrollToEndListener, PullToRefrestListener pullToRefrestListener) {
        this.initContentView(contentView, dataDrawableId, emptyStringId, scrollToEndListener, pullToRefrestListener, null);
    }

    public void initContentView(View contentView, int dataDrawableId, int emptyStringId, ScrollToEndListener scrollToEndListener, PullToRefrestListener pullToRefrestListener, TapToReloadListener tapToReloadListener) {

        if (dataDrawableId != 0) {
            ivEmpty.setImageResource(dataDrawableId);
            ivError.setImageResource(dataDrawableId);
        }

        if (emptyStringId != 0) {
            tvEmpty.setText(emptyStringId);
        }

        if (this.contentView != null) {
            return;
        }
        if (scrollToEndListener != null) {
            this.scrollToEndListener = scrollToEndListener;
            isLoadingMoreSupported = true;
        }

        if (pullToRefrestListener != null) {
            this.pullToRefrestListener = pullToRefrestListener;
            isPullingToRefreshSupported = true;
        }

        if (tapToReloadListener != null) {
            this.tapToReloadListener = tapToReloadListener;
        }
        this.contentView = contentView;
        setUpLoadingMoreIfNeed();
        setUpPullToRefreshIfNeed();
        setTapToReloadIfNeed();
        vContent.addView(contentView, 0);
    }

    private void setTapToReloadIfNeed() {
        vError.setOnClickListener(v -> tapToReloadListener.onTapToReload());
    }

    public void updateViewState(AppDataViewState viewState, String error) {
        this.mError = error;
        updateViewState(viewState);
    }

    public void updateViewState(AppDataViewState viewState) {
        switch (viewState) {
            case SHOW_DATA:
                isDataShown = true;
                vContent.setVisibility(VISIBLE);
                vLoading.setVisibility(GONE);
                vError.setVisibility(GONE);
                vEmpty.setVisibility(GONE);
                vLoadingMore.setVisibility(GONE);
                updateRefreshLayoutState(true);
                break;
            case LOADING:
                vLoading.setVisibility(VISIBLE);
                vError.setVisibility(GONE);
                vContent.setVisibility(GONE);
                vEmpty.setVisibility(GONE);
                updateRefreshLayoutState(false);
                break;
            case HIDE_LOADING:
                vLoading.setVisibility(GONE);
                break;
            case ERROR:
                tvError.setText(mError);
                vError.setVisibility(VISIBLE);
                vLoading.setVisibility(GONE);
                vContent.setVisibility(GONE);
                vEmpty.setVisibility(GONE);
                updateRefreshLayoutState(false);
                break;
            case EMPTY:
                vEmpty.setVisibility(VISIBLE);
                vLoading.setVisibility(GONE);
                vContent.setVisibility(GONE);
                vError.setVisibility(GONE);
                updateRefreshLayoutState(true);
                break;
            case SHOW_LOADING_MORE:
                vLoadingMore.setVisibility(VISIBLE);
                break;
            case HIDE_LOADING_MORE:
                vLoadingMore.setVisibility(GONE);
                break;
            case SHOW_RELOADING:
                swipeRefreshLayout.setRefreshing(true);
                break;
            case HIDE_RELOADING:
                swipeRefreshLayout.setRefreshing(false);
                break;
            case SHOW_TOAST_ERROR:
                Toast.makeText(getContext(), mError, Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void updateRefreshLayoutState(boolean state) {
        if (!isPullingToRefreshSupported) {
            swipeRefreshLayout.setEnabled(false);
            return;
        }
        swipeRefreshLayout.setEnabled(state);
    }

    private void setUpPullToRefreshIfNeed() {
        swipeRefreshLayout.setEnabled(isPullingToRefreshSupported);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (pullToRefrestListener == null) {
                return;
            }
            pullToRefrestListener.onPullToRefresh();
        });
    }

    private void setUpLoadingMoreIfNeed() {

        if (!(contentView instanceof RecyclerView)) {
            return;
        }
        if (!isLoadingMoreSupported) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) contentView;

        if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (scrollToEndListener == null) {
                    return;
                }
                if (dy <= 0) {
                    return;
                }

                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    scrollToEndListener.onScrollToEnd();
                }
            }
        });
    }

    public interface ScrollToEndListener {
        void onScrollToEnd();
    }

    public interface PullToRefrestListener {
        void onPullToRefresh();
    }

    public interface TapToReloadListener {
        void onTapToReload();
    }
}
