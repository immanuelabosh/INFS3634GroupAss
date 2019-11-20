package com.example.geoquiz.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.geoquiz.GeoDB_API_Classes.Country;

import java.util.List;

@Dao
public interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Country> countries);

    @Query("SELECT * FROM Country")
    List<Country> getAllCountries();

    @Query("SELECT * FROM Country WHERE code = :countryID")
    Country getCountry(String countryID);

    @Query("SELECT COUNT(*) FROM Country")
    int getSize();
}
