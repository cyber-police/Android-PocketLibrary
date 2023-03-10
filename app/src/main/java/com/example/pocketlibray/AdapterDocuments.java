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

public class AdapterDocuments extends RecyclerView.Adapter<AdapterDocuments.BookViewHolder> {

    // creating variables for arraylist and context.
    private ArrayList<DocumentsInfo> documentsInfoArrayList;
    private Context context;

    // creating constructor for array list and context.
    public AdapterDocuments(ArrayList<DocumentsInfo> authorsInfoArrayList, Context context) {
        this.documentsInfoArrayList = authorsInfoArrayList;
        this.context = context;
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

        holder.titleTV.setText(documentsInfo.getTitle());
        if (documentsInfo.getIsFree()) {
            holder.priceTV.setText("Free");
        } else {
            holder.priceTV.setText(String.valueOf(documentsInfo.getPrice()));
        }
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
        TextView titleTV, priceTV;
        ImageView bookIV;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.idTVTitle);
            priceTV = itemView.findViewById(R.id.idTVPrice);
            bookIV = itemView.findViewById(R.id.idIVBook);
        }
    }
}