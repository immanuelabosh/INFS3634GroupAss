package com.example.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoquiz.Activities.WebViewActivity;
import com.example.geoquiz.GeoDB_API_Classes.Country;

import java.util.List;

//This is my recycler view adapter, i use it for my search and favourites fragment

// We need to give a type in angle brackets <> when we extend RecyclerView.Adapter
// It's saying that we want an adapter that adapts to CountryViewHolder (our custom ViewHolder)
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    // class variable that holds the data that we want to adapt
    private List<Country> countriesToAdapt;


    public CountryAdapter() {
    }

    public void setData(List<Country> countriesToAdapt) {
        // This is basically a Setter that we use to give data to the adapter
        this.countriesToAdapt = countriesToAdapt;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // First create a View from the layout file. It'll probably be a ViewGroup like
        // ConstraintLayout that contains more Views inside it.
        // This view now represents your entire one item.
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.country, parent, false);

        // Then create an instance of your custom ViewHolder with the View you got from inflating
        // the layout.
        CountryViewHolder countryViewHolder = new CountryViewHolder(view);
        return countryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CountryViewHolder holder, final int position) {
        final Country countryAtPosition = countriesToAdapt.get(position);

        //show the countries name
        holder.countryName.setText(countryAtPosition.getName());

        //when they click on a country item, open the web view activity
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("wikiID", countryAtPosition.getWikiDataId());
                context.startActivity(intent);
            }
        });

        // maybe add a share country function

    }

    @Override
    public int getItemCount() {
        return countriesToAdapt.size();
    }

    public void filterList (List<Country> filteredList){
        countriesToAdapt = filteredList;
        notifyDataSetChanged();
    }

    // ViewHolder represents one item, but doesn't have data when it's first constructed.
    // We assign the data in onBindViewHolder.
    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView countryName;

        // This constructor is used in onCreateViewHolder
        public CountryViewHolder(View v) {
            super(v);  // runs the constructor for the ViewHolder superclass
            view = v;
            countryName = v.findViewById(R.id.countryName);
        }
    }


}
