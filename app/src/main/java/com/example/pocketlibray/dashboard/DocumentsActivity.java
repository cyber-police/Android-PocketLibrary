package com.example.pocketlibray.dashboard;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.pocketlibray.AdapterSpecifiedDocuments;
import com.example.pocketlibray.ConnectionHelper;
import com.example.pocketlibray.MainActivity;
import com.example.pocketlibray.R;
import com.example.pocketlibray.databinding.ActivityDocumentsBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentsActivity extends MainActivity {

    private ActivityDocumentsBinding binding;

    private final int NUMBER_IN_ROW = 3;

    ConnectionHelper connectionHelper;
    RecyclerView mRecyclerView;
    AdapterSpecifiedDocuments adapterSpecifiedDocuments;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDocumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        connectionHelper = new ConnectionHelper();

        mRecyclerView = binding.idRV;
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, NUMBER_IN_ROW);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        binding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        name = getIntent().getStringExtra("name");

        if (name != null) {
            adapterSpecifiedDocuments = new AdapterSpecifiedDocuments(super.documentsInfoArrayList, this, name);
            mRecyclerView.setAdapter(adapterSpecifiedDocuments);
        } else {
            mRecyclerView.setAdapter(super.adapterDocuments);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(this);
        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(menuItem, searchView);
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (name != null) {
                    adapterSpecifiedDocuments.getFilter().filter(newText);
                } else {
                    adapterDocuments.getFilter().filter(newText);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}