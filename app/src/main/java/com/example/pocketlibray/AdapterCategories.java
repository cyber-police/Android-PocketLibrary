package com.example.pocketlibray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.CategoryViewHolder> {

    // creating variables for arraylist and context.
    private ArrayList<CategoriesInfo> categoriesInfoArrayList;
    private Context context;

    // creating constructor for array list and context.
    public AdapterCategories(ArrayList<CategoriesInfo> categoriesInfoArrayList, Context context) {
        this.categoriesInfoArrayList = categoriesInfoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categories, parent, false);
        return new CategoryViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        // inside on bind view holder method we are
        // setting ou data to each UI component.
        CategoriesInfo categoriesInfo = categoriesInfoArrayList.get(position);

        holder.titleTV.setText(categoriesInfo.getTitle());

        // below line is use to add on click listener for our item of recycler view.
        holder.itemView.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).specificClicked(categoriesInfo.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        // inside get item count method we
        // are returning the size of our array list.
        return categoriesInfoArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        // below line is use to initialize
        // our text view and image views.
        TextView titleTV;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.idTVTitle);
        }
    }
}