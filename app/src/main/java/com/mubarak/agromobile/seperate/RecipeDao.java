package com.mubarak.agromobile.seperate;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    List<Recipe> getAll();

    @Insert
    void insert(Recipe recipe);

}