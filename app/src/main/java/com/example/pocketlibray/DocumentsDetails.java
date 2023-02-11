package com.example.pocketlibray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocketlibray.databinding.ActivityBookDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DocumentsDetails extends AppCompatActivity {

    private ActivityBookDetailsBinding binding;

    // creating variables for strings,text view, image views and button.
    String title, language, description, previewLink, buyLink, readLink;
    boolean isAvailable;
    int publishedDate;
    float rating, price;
    private ArrayList<String> authors;
    int pageCount;

    TextView titleTV, languagesTV, publisherTV, descTV, pageTV, publishDateTV, ratingTV;
    Button previewBtn, buyBtn;
    private ImageView bookIV;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookDetailsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_book_details);

        // initializing our views..
        titleTV = findViewById(R.id.idTVTitle);
        languagesTV = findViewById(R.id.idTVLanguages);
        ratingTV = findViewById(R.id.idTVrating);
        publisherTV = findViewById(R.id.idTVpublisher);
        descTV = findViewById(R.id.idTVDescription);
        pageTV = findViewById(R.id.idTVNoOfPages);
        publishDateTV = findViewById(R.id.idTVPublishDate);
        previewBtn = findViewById(R.id.idBtnPreview);
        buyBtn = findViewById(R.id.idBtnBuy);
        bookIV = findViewById(R.id.idIVbook);

        // getting the data which we have passed from our adapter class.
        title = getIntent().getStringExtra("title");
        language = getIntent().getStringExtra("language");
        isAvailable = getIntent().getBooleanExtra("available", false);
        authors = getIntent().getStringArrayListExtra("authors");
        publishedDate = getIntent().getIntExtra("publishedDate", 0);
        description = getIntent().getStringExtra("description");
        pageCount = getIntent().getIntExtra("pageCount", 0);
        rating = getIntent().getFloatExtra("rating", 0);
        price = getIntent().getFloatExtra("price", 0);
        previewLink = getIntent().getStringExtra("previewLink");
        buyLink = getIntent().getStringExtra("buyLink");

        StringBuilder authorsBuilder = new StringBuilder();
        int iterator = 0;
        for (String str : authors) {
            if (iterator++ != authors.size() - 1) {
                authorsBuilder.append(str).append(", ");
            } else {
                authorsBuilder.append(str);
            }
        }
        // after getting the data we are setting
        // that data to our text views and image view.
        titleTV.setText(title);
        languagesTV.setText("Languages: " + language);
        ratingTV.setText("Rating: " + rating);
        publisherTV.setText("Authors: " + authorsBuilder.toString().trim()); //trim() deletes spaces from both ends
        publishDateTV.setText("Published On : " + publishedDate);
        descTV.setText(description);
        pageTV.setText("No Of Pages : " + pageCount);
        Picasso.get().load("https://i.pinimg.com/originals/93/02/32/930232094d590323183bae1ad94c18ce.png").into(bookIV);

        // adding on click listener for our preview button.
        previewBtn.setOnClickListener(v -> {
            if (previewLink.isEmpty()) {
                // below toast message is displayed when preview link is not present.
                Toast.makeText(DocumentsDetails.this, "No preview Link present", Toast.LENGTH_SHORT).show();
                return;
            }
            // if the link is present we are opening
            // that link via an intent.
            Uri uri = Uri.parse(previewLink);
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        });

        // initializing on click listener for buy button.
        buyBtn.setOnClickListener(v -> {
            if (buyLink.isEmpty()) {
                // below toast message is displaying when buy link is empty.
                Toast.makeText(DocumentsDetails.this, "No buy page present for this book", Toast.LENGTH_SHORT).show();
                return;
            }
            // if the link is present we are opening
            // the link via an intent.
            Uri uri = Uri.parse(buyLink);
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        });

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}