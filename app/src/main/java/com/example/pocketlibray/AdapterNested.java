package com.example.pocketlibray;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterNested extends RecyclerView.Adapter<AdapterNested.ParentViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<NestedInfo> itemList;
    private Context context;

    public AdapterNested(ArrayList<NestedInfo> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_nested, viewGroup, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, int position) {

        NestedInfo parentItem = itemList.get(position);

        parentViewHolder.ParentItemTitle.setText(parentItem.getParentItemTitle());
        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder.ChildRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(parentItem.getChildItemList().size());

        AdapterDocuments childItemAdapter = new AdapterDocuments(parentItem.getChildItemList(), context);
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {
        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;

        public ParentViewHolder(final View itemView) {
            super(itemView);
            ParentItemTitle = itemView.findViewById(R.id.parent_item_title);
            ChildRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}