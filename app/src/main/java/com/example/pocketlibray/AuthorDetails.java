package com.example.pocketlibray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketlibray.databinding.ActivityBookDetailsBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuthorDetails extends AppCompatActivity {

    private ActivityBookDetailsBinding binding;

    // creating variables for strings,text view, image views and button.
    String name, living, rating;
    RecyclerView mRecyclerView;
    AdapterSpecifiedDocuments adapterSpecifiedDocuments;

    TextView nameTV, datesOfLivingTV, ratingTV;
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
        authorIV = findViewById(R.id.idIVAuthor);

        // getting the data which we have passed from our adapter class.
        name = getIntent().getStringExtra("name");
        living = getIntent().getStringExtra("living");
        rating = getIntent().getStringExtra("rating");

        // after getting the data we are setting
        // that data to our text views and image view.
        nameTV.setText(name);
        datesOfLivingTV.setText(living);
        ratingTV.setText(rating);
        Picasso.get().load("https://static-00.iconduck.com/assets.00/incognito-icon-512x487-o2l6p9u6.png").into(authorIV);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        mRecyclerView = findViewById(R.id.child_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapterSpecifiedDocuments = new AdapterSpecifiedDocuments(MainActivity.documentsInfoArrayList, this, name);
        mRecyclerView.setAdapter(adapterSpecifiedDocuments);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> new RespondFragment(this, getString(R.string.leave_respond_document) + " author!").show(getSupportFragmentManager(), ""));
    }
}