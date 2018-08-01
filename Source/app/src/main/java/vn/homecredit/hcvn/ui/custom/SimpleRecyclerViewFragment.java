package vn.homecredit.hcvn.ui.custom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.utils.imageLoader.ImageLoader;

public class SimpleRecyclerViewFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
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
        ImageAdapter imageAdapter = new ImageAdapter(images);
        recyclerView.setAdapter(imageAdapter);
    }

    public class ImageAdapter extends RecyclerView.Adapter {
        private List<String> items = new ArrayList<>();

        public ImageAdapter(List<String> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ImageViewHolder) holder).bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(List<String> items) {
            this.items = items;
            notifyDataSetChanged();
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @Inject ImageLoader imageLoader;
        ImageView iv;

        public ImageViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);

        }
        public void bind(String url) {
            imageLoader.loadImage(getContext(), iv, url);
        }
    }



}
