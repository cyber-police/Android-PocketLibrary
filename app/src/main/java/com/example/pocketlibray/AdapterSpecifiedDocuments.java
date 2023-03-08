package com.example.pocketlibray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterSpecifiedDocuments extends RecyclerView.Adapter<AdapterSpecifiedDocuments.BookViewHolder> {

    // creating variables for arraylist and context.
    private ArrayList<DocumentsInfo> documentsInfoArrayList;
    private Context context;
    private String chosenSpecific;

    // creating constructor for array list and context.
    public AdapterSpecifiedDocuments(ArrayList<DocumentsInfo> authorsInfoArrayList, Context context, String chosenSpecific) {
        this.documentsInfoArrayList = authorsInfoArrayList;
        this.context = context;
        this.chosenSpecific = chosenSpecific;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_documents, parent, false);
        return new BookViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        // inside on bind view holder method we are
        // setting ou data to each UI component.
        DocumentsInfo documentsInfo = documentsInfoArrayList.get(position);

        if (Objects.equals(documentsInfo.getCategory(), chosenSpecific) || documentsInfo.getAuthors().contains(chosenSpecific)) {

            StringBuilder authorsBuilder = new StringBuilder();
            int iterator = 0;
            for (String str : documentsInfo.getAuthors()) {
                if (iterator++ != documentsInfo.getAuthors().size() - 1) {
                    authorsBuilder.append(str).append(", ");
                } else {
                    authorsBuilder.append(str);
                }
            }

            holder.titleTV.setText(documentsInfo.getTitle());
            holder.authorsTV.setText(authorsBuilder.toString().trim());
            holder.pageCountTV.setText("No of Pages : " + documentsInfo.getPageCount());
            holder.dateTV.setText(String.valueOf(documentsInfo.getPublishedDate()));
            holder.ratingTV.setText(String.valueOf(documentsInfo.getRating()));
            // below line is use to set image from URL in our image view.
            Picasso.get().load("https://i.pinimg.com/originals/93/02/32/930232094d590323183bae1ad94c18ce.png").into(holder.bookIV);

            // below line is use to add on click listener for our item of recycler view.
            holder.itemView.setOnClickListener(v -> {
                // inside on click listener method we are calling a new activity
                // and passing all the data of that item in next intent.
                Intent i = new Intent(context, DocumentsDetails.class);
                i.putExtra("title", documentsInfo.getTitle());
                i.putExtra("language", documentsInfo.getLanguage());
                i.putExtra("type", documentsInfo.getType());
                i.putExtra("available", documentsInfo.getAvailability());
                i.putExtra("authors", documentsInfo.getAuthors());
                i.putExtra("rating", documentsInfo.getRating());
                i.putExtra("publishedDate", documentsInfo.getPublishedDate());
                i.putExtra("description", documentsInfo.getDescription());
                i.putExtra("pageCount", documentsInfo.getPageCount());
                if (!documentsInfo.getIsFree()) {
                    i.putExtra("price", documentsInfo.getPrice());
                }
                i.putExtra("previewLink", documentsInfo.getPreviewLink());
                i.putExtra("buyLink", documentsInfo.getBuyLink());

                // after passing that data we are
                // starting our new  intent.
                context.startActivity(i);
            });
        } else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    @Override
    public int getItemCount() {
        // inside get item count method we
        // are returning the size of our array list.
        return documentsInfoArrayList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        // below line is use to initialize
        // our text view and image views.
        TextView titleTV, authorsTV, pageCountTV, dateTV, ratingTV;
        ImageView bookIV;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.idTVTitle);
            //authorsTV = itemView.findViewById(R.id.idTVAuthors);
            //pageCountTV = itemView.findViewById(R.id.idTVPageCount);
            dateTV = itemView.findViewById(R.id.idTVDate);
            //ratingTV = itemView.findViewById(R.id.idTVRating);
            bookIV = itemView.findViewById(R.id.idIVBook);
        }
    }
}