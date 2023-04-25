package com.example.pocketlibray;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketlibray.databinding.ActivityBookDetailsBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuthorDetails extends MainActivity {

    private ActivityBookDetailsBinding binding;

    // creating variables for strings,text view, image views and button.
    String name, living, rating, biography, photo;
    ArrayList<String> languages;
    RecyclerView mRecyclerView;
    AdapterSpecifiedDocuments adapterSpecifiedDocuments;

    TextView nameTV, datesOfLivingTV, ratingTV, biographyTV, languagesTV;
    ImageView authorIV;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookDetailsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_author_details);

        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        // initializing our views..
        nameTV = findViewById(R.id.idTVTitle);
        datesOfLivingTV = findViewById(R.id.idYearsOfLiving);
        ratingTV = findViewById(R.id.idTVRating);
        languagesTV = findViewById(R.id.idTVLanguages);
        authorIV = findViewById(R.id.idIVAuthor);
        biographyTV = findViewById(R.id.idTVBiography);

        // getting the data which we have passed from our adapter class.
        name = getIntent().getStringExtra("name");
        living = getIntent().getStringExtra("living");
        languages = getIntent().getStringArrayListExtra("languages");
        rating = getIntent().getStringExtra("rating");
        biography = getIntent().getStringExtra("biography");
        photo = getIntent().getStringExtra("photo");

        StringBuilder languagesBuilder = new StringBuilder();
        int iterator = 0;
        for (String str : languages) {
            if (iterator++ != languages.size() - 1) {
                languagesBuilder.append(str).append(", ");
            } else {
                languagesBuilder.append(str);
            }
        }

        // after getting the data we are setting
        // that data to our text views and image view.
        nameTV.setText(name);
        datesOfLivingTV.setText(living);
        ratingTV.setText(rating);
        languagesTV.setText("Languages: " + languagesBuilder.toString().trim());
        biographyTV.setText(biography);
        Picasso.get().load(photo).into(authorIV);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        mRecyclerView = findViewById(R.id.child_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapterSpecifiedDocuments = new AdapterSpecifiedDocuments(super.documentsInfoArrayList, this, name);
        mRecyclerView.setAdapter(adapterSpecifiedDocuments);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> new RespondFragment(this, getString(R.string.leave_respond_document) + " author!").show(getSupportFragmentManager(), ""));
    }
}