package com.example.pocketlibray.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketlibray.AdapterCategories;
import com.example.pocketlibray.AdapterSpecifiedDocuments;
import com.example.pocketlibray.AuthorsInfo;
import com.example.pocketlibray.CategoriesInfo;
import com.example.pocketlibray.ConnectionHelper;
import com.example.pocketlibray.MainActivity;
import com.example.pocketlibray.R;
import com.example.pocketlibray.databinding.ActivityDocumentsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

public class CategoriesActivity extends AppCompatActivity {

    private ActivityDocumentsBinding binding;

    private final StringBuilder text = new StringBuilder();
    private ArrayList<CategoriesInfo> categoriesInfoArrayList;

    Connection connection;
    ConnectionHelper connectionHelper;
    RecyclerView mRecyclerView;
    CategoriesInfo categoriesInfo;
    AdapterCategories adapterCategories;
    AdapterSpecifiedDocuments adapterSpecifiedDocuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDocumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        connectionHelper = new ConnectionHelper();
        categoriesInfoArrayList = new ArrayList<>();

        mRecyclerView = binding.idRV;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        binding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        getTextFromSQL();
    }

    public void getTextFromSQL() {
        try {
            connection = connectionHelper.connectionClass();
            if (connection != null) {

                InputStream inputStream = this.getResources().openRawResource(R.raw.query);

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    System.out.println(reader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        text.append(line).append('\n');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Statement categoriesStatement = connection.createStatement();
                ResultSet categoriesResultSet = categoriesStatement.executeQuery("SELECT * FROM file_category");
                while (categoriesResultSet.next()) {
                    String categoryTitle = categoriesResultSet.getString("file_category");

                    categoriesInfo = new CategoriesInfo(categoryTitle);
                    categoriesInfoArrayList.add(categoriesInfo);
                }
                categoriesInfoArrayList.sort(Comparator.comparing(CategoriesInfo::getTitle));
                adapterCategories = new AdapterCategories(categoriesInfoArrayList, this);
                mRecyclerView.setAdapter(adapterCategories);
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
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
                adapterCategories.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}