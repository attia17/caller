package com.example.caller.ui.main;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.caller.database.ContactDao;
import com.example.caller.database.MyDataBase;
import com.example.caller.models.Contact;

import java.util.List;

public class DataRepository {
    private ContactDao dao;
    private LiveData<List<Contact>> liveData;

    public DataRepository(Application app) {
        MyDataBase dataBase = MyDataBase.getInstance (app);
        dao = dataBase.contactDao ();
        liveData = dao.getAllContact ();
    }
    public LiveData<List<Contact>> getAllData(){
        return liveData;
    }

    public void insertContact(Contact contact){
        new InsertAsyncTask (dao).execute (contact);
    }

    public void deleteContact (Contact contact){
        new DeleteAsyncTask (dao).execute (contact);

    }
    public void update(Contact contact){
        new UpdateAsyncTask (dao).execute (contact);

    }
    public void deleteAll(){
        new DeleteAllAsyncTask (dao).execute ();

    }

    private static class InsertAsyncTask extends AsyncTask<Contact,Void,Void> {
        private ContactDao dao;

        InsertAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... contact) {
            dao.insertContact (contact[0]);
            return null;

        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Contact,Void,Void>{
        ContactDao dao;

        DeleteAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            dao.deleteContact (contacts[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Contact,Void,Void>{
        ContactDao dao;

        UpdateAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            dao.update (contacts[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        ContactDao dao;

        DeleteAllAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll ();
            return null;
        }
    }

}
