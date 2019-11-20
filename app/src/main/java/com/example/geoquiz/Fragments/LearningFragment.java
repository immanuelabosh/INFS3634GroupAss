package com.example.geoquiz.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoquiz.CountryAdapter;
import com.example.geoquiz.Database.AppDatabase;
import com.example.geoquiz.GeoDB_API_Classes.Country;
import com.example.geoquiz.R;

import java.util.ArrayList;
import java.util.List;

public class LearningFragment extends Fragment {

    RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_country_recycler, container, false);

        recyclerView = view.findViewById(R.id.CountryRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //defining searchbar as an edit text
        EditText searchText = view.findViewById(R.id.searchBar);

        AppDatabase db = AppDatabase.getInstance(getContext());
        final List<Country> countryList = db.countryDao().getAllCountries();

        final CountryAdapter countryAdapter = new CountryAdapter();
        countryAdapter.setData(countryList);

        recyclerView.setAdapter(countryAdapter);


        //search text reaction after user has changed the searchText field
        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //creates a new list to display when user filters
                List<Country> filteredList = new ArrayList<>();
                for (Country country : countryList) {
                    if (country.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredList.add(country);
                    }
                }

                countryAdapter.filterList(filteredList);
                recyclerView.setAdapter(countryAdapter);
            }
        });


        return view;
    }
}
