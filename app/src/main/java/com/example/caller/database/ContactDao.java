package com.example.caller.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.caller.models.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insertContact(Contact contact);

    @Update
    void update (Contact contact);

    @Delete()
    void deleteContact(Contact contact);

    @Query("select * from Contact")
    LiveData<List<Contact>> getAllContact();


    @Query("select * from Contact where name in (:contactNames)")
    LiveData<List<Contact>> findByName(String[] contactNames);

    @Query("DELETE FROM Contact")
    void deleteAll();


}
