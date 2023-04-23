package com.example.pocketlibray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketlibray.dashboard.AuthorsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Function;

public class AdapterAuthors extends RecyclerView.Adapter<AdapterAuthors.AuthorViewHolder> implements Filterable {

    // creating variables for arraylist and context.
    private ArrayList<AuthorsInfo> authorsInfoArrayList;
    private ArrayList<AuthorsInfo> authorsInfoArrayListCopy;
    private ArrayList<DocumentsInfo> documentsInfoArrayList;
    private Context context;
    private float authorsRating, numberOfWrittenDocuments;

    // creating constructor for array list and context.
    public AdapterAuthors(ArrayList<AuthorsInfo> authorsInfoArrayList, ArrayList<DocumentsInfo> documentsInfoArrayList, Context context) {
        this.authorsInfoArrayList = authorsInfoArrayList;
        this.authorsInfoArrayListCopy = authorsInfoArrayList;
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
        authorsRating = 0;
        numberOfWrittenDocuments = 0;

        AuthorsInfo authorsInfo = authorsInfoArrayList.get(position);

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
        authorsInfo.setRating(Float.toString(authorsRating / numberOfWrittenDocuments));
        holder.ratingTV.setText(authorsInfo.getRating());
        // below line is use to set image from URL in our image view.
        Picasso.get().load("https://static-00.iconduck.com/assets.00/incognito-icon-512x487-o2l6p9u6.png").into(holder.authorIV);
        // change image color using themes
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValue, true);
        holder.authorIV.setColorFilter(typedValue.data);

        // below line is use to add on click listener for our item of recycler view.
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, AuthorDetails.class);
            i.putExtra("name", authorsInfo.getName());
            if (authorsInfo.getStatus()) {
                i.putExtra("living", authorsInfo.getBirthDate() + "-" + authorsInfo.getDeathDate());
            } else {
                i.putExtra("living", authorsInfo.getBirthDate());
            }
            i.putExtra("rating", authorsInfo.getRating());
            // after passing that data we are
            // starting our new  intent.
            context.startActivity(i);

//            if (context instanceof AuthorsActivity) {
//                ((AuthorsActivity) context).specificClicked(authorsInfo.getName());
//            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = authorsInfoArrayListCopy;
                    filterResults.count = authorsInfoArrayListCopy.size();
                } else {
                    String searchString = constraint.toString().toUpperCase(Locale.ROOT);
                    ArrayList<AuthorsInfo> filteredArrayList = new ArrayList<>();
                    for (AuthorsInfo authorsInfo : authorsInfoArrayListCopy) {
                        if (authorsInfo.getName().toUpperCase(Locale.ROOT).contains(searchString)) {
                            filteredArrayList.add(authorsInfo);
                        }
                    }
                    filterResults.values = filteredArrayList;
                    filterResults.count = filteredArrayList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                authorsInfoArrayList = (ArrayList<AuthorsInfo>) results.values;
                notifyDataSetChanged();
            }
        };
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