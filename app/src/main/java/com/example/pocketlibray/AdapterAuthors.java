package com.example.pocketlibray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.function.Function;

public class AdapterAuthors extends RecyclerView.Adapter<AdapterAuthors.AuthorViewHolder> {

    // creating variables for arraylist and context.
    private ArrayList<AuthorsInfo> authorsInfoArrayList;
    private ArrayList<DocumentsInfo> documentsInfoArrayList;
    private Context context;

    // creating constructor for array list and context.
    public AdapterAuthors(ArrayList<AuthorsInfo> authorsInfoArrayList, ArrayList<DocumentsInfo> documentsInfoArrayList, Context context) {
        this.authorsInfoArrayList = authorsInfoArrayList;
        this.documentsInfoArrayList = documentsInfoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_authors, parent, false);
        return new AuthorViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {

        // inside on bind view holder method we are
        // setting ou data to each UI component.
        AuthorsInfo authorsInfo = authorsInfoArrayList.get(position);
        float authorsRating = 0, numberOfWrittenDocuments = 0;

        holder.nameTV.setText(authorsInfo.getName());
        if (authorsInfo.getStatus()) {
            holder.datesOfLivingTV.setText(authorsInfo.getBirthDate() + "-" + authorsInfo.getDeathDate());
        } else {
            holder.datesOfLivingTV.setText(authorsInfo.getBirthDate());
        }
        for (DocumentsInfo documentsInfo : documentsInfoArrayList) {
            if (documentsInfo.getAuthors().contains(authorsInfo.getName())) {
                numberOfWrittenDocuments++;
                authorsRating += documentsInfo.getRating();
            }
        }
        holder.ratingTV.setText(Float.toString(authorsRating / numberOfWrittenDocuments));
        // below line is use to set image from URL in our image view.
        Picasso.get().load("https://static-00.iconduck.com/assets.00/incognito-icon-512x487-o2l6p9u6.png").into(holder.authorIV);
        // change image color using themes
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValue, true);
        holder.authorIV.setColorFilter(typedValue.data);

        // below line is use to add on click listener for our item of recycler view.
        holder.itemView.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).specificClicked(authorsInfo.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        // inside get item count method we
        // are returning the size of our array list.
        return authorsInfoArrayList.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder {
        // below line is use to initialize
        // our text view and image views.
        TextView nameTV, datesOfLivingTV, ratingTV;
        ImageView authorIV;

        public AuthorViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idTVName);
            datesOfLivingTV = itemView.findViewById(R.id.idTVDate);
            ratingTV = itemView.findViewById(R.id.idTVAuthorRating);
            authorIV = itemView.findViewById(R.id.idIVAuthor);
        }
    }
}