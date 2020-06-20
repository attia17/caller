package com.example.caller.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.caller.models.Contact;

import java.util.List;

public class DataRepository {
    MyDataBase dataBase;
    private LiveData<List<Contact>> listLiveData;

    public DataRepository(Application app) {
        dataBase = MyDataBase.getInstance (app);
        dataBase.contactDao ().getAllContact ();

    }

    public LiveData<List<Contact>> getAllData() {
        return listLiveData;
    }

    public void insertContact(Contact contact) {
        new InsertAsyncTask (dataBase).execute (contact);
    }

    public void deleteContact(Contact contact) {
        new DeleteAsyncTask (dataBase).execute (contact);

    }

    public void update(Contact contact) {
        new UpdateAsyncTask (dataBase).execute (contact);

    }

    public void deleteAll() {
        new DeleteAllAsyncTask (dataBase).execute ();

    }

    private static class InsertAsyncTask extends AsyncTask<Contact, Void, Void> {
        private MyDataBase dataBase;

        InsertAsyncTask(MyDataBase dataBase) {
            this.dataBase = dataBase;
        }

        @Override
        protected Void doInBackground(Contact... contact) {
            dataBase.contactDao ().insertContact (contact[0]);
            return null;

        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Contact, Void, Void> {
        MyDataBase dataBase;

        DeleteAsyncTask(MyDataBase dataBase) {
            this.dataBase = dataBase;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            dataBase.contactDao ().deleteContact (contacts[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Contact, Void, Void> {
       MyDataBase dataBase;

        UpdateAsyncTask(MyDataBase dataBase) {
            this.dataBase = dataBase;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            dataBase.contactDao ().update (contacts[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        MyDataBase dataBase;

        DeleteAllAsyncTask(MyDataBase dataBase) {
            this.dataBase = dataBase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataBase.contactDao ().deleteAll ();
            return null;
        }
    }

}
