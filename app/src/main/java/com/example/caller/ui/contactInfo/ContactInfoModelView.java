package com.example.caller.ui.contactInfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.caller.database.DataRepository;
import com.example.caller.models.Contact;

import java.util.List;

public class ContactInfoModelView extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<Contact>> liveData;

    public ContactInfoModelView(@NonNull Application application) {
        super (application);
        repository = new DataRepository (application);
        liveData = repository.getAllData ();
    }

    public LiveData<List<Contact>> getLiveData() {
        return liveData;

    }

    public void insert(Contact contact) {
        repository.insertContact (contact);
    }

    public void delete(Contact contact) {
        repository.deleteContact (contact);
    }

    public void update(Contact contact) {
        repository.update (contact);
    }

    public void deleteAll(Contact contact) {
        repository.deleteAll ();
    }
}