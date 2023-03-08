package com.example.pocketlibray.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketlibray.AdapterNested;
import com.example.pocketlibray.ConnectionHelper;
import com.example.pocketlibray.DocumentsInfo;
import com.example.pocketlibray.NestedInfo;
import com.example.pocketlibray.R;
import com.example.pocketlibray.databinding.FragmentHomeBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private final StringBuilder text = new StringBuilder();
    private ArrayList<DocumentsInfo> documentsInfoArrayList;

    Connection connection;
    ConnectionHelper connectionHelper;
    DocumentsInfo documentsInfo;
    AdapterNested adapterNested;
    RecyclerView recyclerView;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        recyclerView = binding.idRV;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        connectionHelper = new ConnectionHelper();
        documentsInfoArrayList = new ArrayList<>();

        getTextFromSQL();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
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
                    }
                }
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        }
        adapterNested = new AdapterNested(ParentItemList(), root.getContext());
        recyclerView.setAdapter(adapterNested);
    }

    private ArrayList<NestedInfo> ParentItemList() {
        ArrayList<NestedInfo> itemList = new ArrayList<>();
        ArrayList<DocumentsInfo> topRatedArrayList = new ArrayList<>(documentsInfoArrayList);
        DocumentsInfo reservStore;
        ArrayList<DocumentsInfo> mostReadArrayList = new ArrayList<>(documentsInfoArrayList);

        for (int checkingElementPos = 0; checkingElementPos < documentsInfoArrayList.size() - 1; checkingElementPos++) {
            for (int checkingElementPos2 = documentsInfoArrayList.size() - 1; checkingElementPos2 >= checkingElementPos + 1; checkingElementPos2--) {
                if (topRatedArrayList.get(checkingElementPos2 - 1).getRating() < topRatedArrayList.get(checkingElementPos2).getRating()) {
                    reservStore = topRatedArrayList.get(checkingElementPos2 - 1);
                    topRatedArrayList.set(checkingElementPos2 - 1, topRatedArrayList.get(checkingElementPos2));
                    topRatedArrayList.set(checkingElementPos2, reservStore);
                }
                if (mostReadArrayList.get(checkingElementPos2 - 1).getReadTimes() < mostReadArrayList.get(checkingElementPos2).getReadTimes()) {
                    reservStore = mostReadArrayList.get(checkingElementPos2 - 1);
                    mostReadArrayList.set(checkingElementPos2 - 1, mostReadArrayList.get(checkingElementPos2));
                    mostReadArrayList.set(checkingElementPos2, reservStore);
                }
            }
        }

        NestedInfo item = new NestedInfo("Most Read", mostReadArrayList);
        itemList.add(item);
        NestedInfo item1 = new NestedInfo("Top Rated", topRatedArrayList);
        itemList.add(item1);

        topRatedArrayList.clear();
        mostReadArrayList.clear();
        
        for (DocumentsInfo documentsInfo : documentsInfoArrayList) {
            if (!documentsInfo.getIsFree()) {
                topRatedArrayList.add(documentsInfo);
            } else {
                mostReadArrayList.add(documentsInfo);
            }
        }
        NestedInfo item2 = new NestedInfo("Free", mostReadArrayList);
        itemList.add(item2);
        NestedInfo item3 = new NestedInfo("Paid", topRatedArrayList);
        itemList.add(item3);

        return itemList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}