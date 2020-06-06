package com.example.caller.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.caller.models.Contact;


@Database(entities = {Contact.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {

    private final static String DB_NAME = "contactDB";
    private static MyDataBase instance;
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback () {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate (db);
            new PopulateDataAsyncTask (instance).execute ();

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen (db);
        }
    };

    public static synchronized MyDataBase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder (context.getApplicationContext (),
                    MyDataBase.class, "DB_NAME")
                    .fallbackToDestructiveMigration ()
                    .addCallback (callback)
                    .build ();
        }
        return instance;
    }
//لربط الرووم مع ال dao
 public abstract ContactDao contactDao();

    private static class PopulateDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContactDao contactDao;

        PopulateDataAsyncTask(MyDataBase db) {
            contactDao = db.contactDao ();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.insertContact (new Contact ("M.attia", "01099763920"));
            return null;
        }
    }
}

