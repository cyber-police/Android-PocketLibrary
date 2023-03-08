package com.example.pocketlibray;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.example.pocketlibray.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<DocumentsInfo> documentsInfoArrayList;
    private ArrayList<AuthorsInfo> authorsInfoArrayList;
    private ArrayList<CategoriesInfo> categoriesInfoArrayList;
    private final StringBuilder text = new StringBuilder();
    private int currentTabPosition;

    Connection connection;
    ConnectionHelper connectionHelper;
    RecyclerView mRecyclerView;
    DocumentsInfo documentsInfo;
    AuthorsInfo authorsInfo;
    CategoriesInfo categoriesInfo;
    AdapterDocuments adapterDocuments;
    AdapterAuthors adapterAuthors;
    AdapterCategories adapterCategories;
    AdapterSpecifiedDocuments adapterSpecifiedDocuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        mRecyclerView = findViewById(R.id.idRV);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
        connectionHelper = new ConnectionHelper();
        documentsInfoArrayList = new ArrayList<>();
        authorsInfoArrayList = new ArrayList<>();
        categoriesInfoArrayList = new ArrayList<>();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);

        //getTextFromSQL();
    }

    private void fillCategories(int param) {
        currentTabPosition = param;
        if (param == 0) {
            mRecyclerView.setAdapter(adapterDocuments);
        } else if (param == 1) {
            mRecyclerView.setAdapter(adapterAuthors);
        } else if (param == 2) {
            mRecyclerView.setAdapter(adapterCategories);
        }
    }

    public void specificClicked(String chosenSpecific) {
        adapterSpecifiedDocuments = new AdapterSpecifiedDocuments(documentsInfoArrayList, MainActivity.this, chosenSpecific);
        mRecyclerView.setAdapter(adapterSpecifiedDocuments);
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

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(String.valueOf(text));
                while (resultSet.next()) {
                    String title = resultSet.getString("file_name");
                    String language = resultSet.getString("file_language");
                    String category = resultSet.getString("file_category");
                    String type = resultSet.getString("type_file");
                    int readTimes = resultSet.getInt("how_many_times_read");
                    boolean availability = resultSet.getBoolean("is_available");
                    ArrayList<String> authorsArray = new ArrayList<>();
                    authorsArray.add(resultSet.getString("file_author"));
                    float rating = resultSet.getFloat("rating");
                    int publishedDate = resultSet.getInt("year_published");
                    String description = resultSet.getString("file_description");
                    int pageCount = resultSet.getInt("number_of_pages");
                    boolean isFree = resultSet.getBoolean("is_free");
                    int price = 0;
                    if (!isFree) {
                        price = resultSet.getInt("price");
                    }
                    //String infoLink =

                    // many-to-many connection
                    documentsInfo = new DocumentsInfo(title, language, category, type, readTimes, availability, authorsArray, rating, publishedDate, description, pageCount, price, isFree);
                    if (documentsInfoArrayList.isEmpty()) {
                        documentsInfoArrayList.add(documentsInfo);
                    } else {
                        boolean isEqual = false;
                        for (int checkingElementPos = 0; checkingElementPos < documentsInfoArrayList.size(); checkingElementPos++) {
                            if (Objects.equals(documentsInfoArrayList.get(checkingElementPos).getTitle(), title)) {
                                documentsInfoArrayList.get(checkingElementPos).getAuthors().addAll(authorsArray);
                                isEqual = true;
                            }
                        }
                        if (!isEqual) {
                            documentsInfoArrayList.add(documentsInfo);
                        }
                        adapterDocuments = new AdapterDocuments(documentsInfoArrayList, MainActivity.this);
                    }
                }
                Statement authorsStatement = connection.createStatement();
                ResultSet authorsResultSet = authorsStatement.executeQuery("SELECT * FROM file_author");
                while (authorsResultSet.next()) {
                    String name = authorsResultSet.getString("file_author");
                    String birthDate = authorsResultSet.getString("date_of_birth");
                    String deathDate = "";
                    boolean isDead = authorsResultSet.getBoolean("is_dead");
                    if (isDead) {
                        deathDate = authorsResultSet.getString("date_death");
                    }
                    authorsInfo = new AuthorsInfo(name, birthDate, isDead, deathDate);
                    authorsInfoArrayList.add(authorsInfo);

                    adapterAuthors = new AdapterAuthors(authorsInfoArrayList, documentsInfoArrayList, MainActivity.this);
                }

                Statement categoriesStatement = connection.createStatement();
                ResultSet categoriesResultSet = categoriesStatement.executeQuery("SELECT * FROM file_category");
                while (categoriesResultSet.next()) {
                    String categoryTitle = categoriesResultSet.getString("file_category");

                    categoriesInfo = new CategoriesInfo(categoryTitle);
                    categoriesInfoArrayList.add(categoriesInfo);

                    adapterCategories = new AdapterCategories(categoriesInfoArrayList, MainActivity.this);
                }
                mRecyclerView.setAdapter(adapterDocuments);
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void leaveResponse(View view) {
        new RespondFragment(this, getString(R.string.leave_respond_app)).show(getSupportFragmentManager(), "");
    }
}