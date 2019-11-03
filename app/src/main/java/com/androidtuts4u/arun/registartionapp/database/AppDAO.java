package com.example.db;


import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.androidtuts4u.arun.registartionapp.database.Entity;

import java.util.List;

@android.arch.persistence.room.Dao
public interface AppDAO {

    @Query("Select * from app")
    List<Entity> getAllPlayers();

    @Query("Select * from app where name LIKE :Name")
    List<Entity> findByName(String Name);

    @Query("Select * from app where id LIKE :Id")
    Entity findById(int Id);

    @Query("Select * from app where parent_id LIKE :Parent_id")
    List<Entity> findbyParentId(int Parent_id);

    @Query("Select * from app where description LIKE :Description")
    List<Entity> findbyDescription(String Description);

    @Query("Select * from app where date LIKE :Date")
    List<Entity> findbyDate(String Date);

    @Query("Select COUNT(*) from app")
    int countPlayers();

    @Query("DELETE From app")
    void purge();

    @Insert
    void insertAll(Entity... app);

    @Delete
    void delete(Entity... app);
}