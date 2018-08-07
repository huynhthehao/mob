package vn.homecredit.hcvn.ui.custom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.utils.imageLoader.ImageLoader;

public class SimpleRecyclerViewFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private View footerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefresh = view.findViewById(R.id.swiperefresh);
        swipeRefresh.setEnabled(false);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void updateImages(List<String> images) {
        if (images == null) {
            images = new ArrayList<>();
        }
        ImageAdapter imageAdapter = new ImageAdapter(images, footerView, (items, startPosition) -> new ImageViewer.Builder(getActivity(), items)
                .setStartPosition(startPosition)
                .show());
        recyclerView.setAdapter(imageAdapter);
    }

    public void addFooterView(View view) {
        footerView = view;
    }

    public class ImageAdapter extends RecyclerView.Adapter {
        public static final int TYPE_CONTENT = 1;
        public static final int TYPE_FOOTER = 2;
        private List<String> items = new ArrayList<>();
        private View footerView;
        OnImageClickListener listener;

        public ImageAdapter(List<String> items) {
            this.items = items;
        }

        public ImageAdapter(List<String> items, View footerView, OnImageClickListener listener) {
            this.items = items;
            this.footerView = footerView;
            this.listener = listener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            if (viewType == TYPE_FOOTER) {
                return new FooterViewHolder(footerView);
            } else {
                View view = layoutInflater.inflate(R.layout.item_image, parent, false);
                return new ImageViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ImageViewHolder) {
                ((ImageViewHolder) holder).bind(items.get(position), position);
            }
        }

        @Override
        public int getItemCount() {
            if (items == null) {
                return 0;
            }
            return footerView == null ? items.size() : items.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == items.size()) {
                return TYPE_FOOTER;
            }
            return super.getItemViewType(position);
        }

        public void setItems(List<String> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView iv;

            public ImageViewHolder(View itemView) {
                super(itemView);
                iv = itemView.findViewById(R.id.iv);
            }

            public void bind(String url, int position) {
                ImageLoader.loadImage(getContext(), iv, url);
                iv.setOnClickListener(view -> listener.onImageClick(items, position));
            }
        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnImageClickListener {
        void onImageClick(List<String> items, int startPosition);
    }


}
