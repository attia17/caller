package com.example.caller.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caller.R;
import com.example.caller.adapters.AdapterContact;
import com.example.caller.models.Contact;
import com.example.caller.ui.contactInfo.ContactInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
        public MainViewModel modelView;
    private RecyclerView recyclerView;
    private FloatingActionButton button;
    private AdapterContact adapterContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        intiView ();
        intiAdapter ();
//        modelView = ViewModelProviders.of (this).get (MainViewModel.class);
        modelView =  new ViewModelProvider (this).get (MainViewModel.class);
        modelView.getLiveData ().observe (this, new Observer<List<Contact>> () {
            @Override
            public void onChanged(List<Contact> contacts) {
                if (contacts.size () == 0) {
                    Toast.makeText (MainActivity.this, "no Data to show", Toast.LENGTH_SHORT).show ();
                }
                //sortList
                Collections.sort (contacts, new Comparator<Contact> () {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return o1.getName ().compareTo (o2.getName ());
                    }
                });
                adapterContact.setList (contacts);

            }
        });
        button.setOnClickListener (this);

        //swipe
        adapterContact.setOnItemClick (new AdapterContact.OnItemClick () {
            @Override
            public void onClick(Contact contact) {
                passInfo (contact);
            }
        });
        new ItemTouchHelper (new ItemTouchHelper.SimpleCallback (0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction==8) {
                    Toast.makeText (MainActivity.this, "calling   "
                            +adapterContact.getItemAt (viewHolder.getAdapterPosition ()).getName (), Toast.LENGTH_SHORT).show ();
                    adapterContact.notifyDataSetChanged ();


                }
                if (direction==4) {
                    Toast.makeText (MainActivity.this, "message"+adapterContact.getItemAt (viewHolder.getAdapterPosition ()).getName (), Toast.LENGTH_SHORT).show ();
                    adapterContact.notifyDataSetChanged ();
                }


            }
        }).attachToRecyclerView (recyclerView);

    }

    protected void intiView() {
        recyclerView = findViewById (R.id.RV_accounts);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));
        recyclerView.setHasFixedSize (true);
        button = findViewById (R.id.btn_add);
    }

    protected void intiAdapter() {
        adapterContact = new AdapterContact ();
        recyclerView.setAdapter (adapterContact);
    }

    private void passInfo(Contact contact) {
        Intent intent = new Intent (MainActivity.this, ContactInfo.class);
        intent.putExtra (ContactInfo.EXTRA_ID, contact.getId ());
        intent.putExtra (ContactInfo.EXTRA_NAME, contact.getName ());
        intent.putExtra (ContactInfo.EXTRA_PHONE, contact.getPhone ());
        startActivity (intent);
    }


    @Override
    public void onClick(View v) {
        if (v.getId () == R.id.btn_add) {
            addContact ();
        }
    }


    //contextMen
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo ();
        switch (item.getItemId ()) {
            case 1:
                removeItem (item.getGroupId ());
                return true;

            case 2:
                Toast.makeText (this, item.getGroupId () + "", Toast.LENGTH_SHORT).show ();
                return true;

            case 3:
                help ();
                return true;
            default:
                return super.onContextItemSelected (item);

        }
    }

    //addContact
    protected void addContact() {
        Intent intent = new Intent (MainActivity.this, ContactInfo.class);
        startActivity (intent);
    }

    //help
    protected void help() {
        Toast.makeText (MainActivity.this, "call me", Toast.LENGTH_SHORT).show ();
        Intent in = new Intent (MainActivity.this, ContactInfo.class);
        startActivity (in);
        finish ();

    }

    //remove
    protected void removeItem(int position) {
        modelView.delete (adapterContact.getItemAt (position));
        Toast.makeText (MainActivity.this,
                "Contact removed", Toast.LENGTH_SHORT).show ();
    }

    //    menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.option_menu_items, menu);
        MenuItem searchItem = menu.findItem (R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView ();
        searchView.setImeOptions (EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterContact.getFilter ().filter (newText);
                return false;
            }
        });
        return true;
    }

    //onSelectMenuItem
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.item_add_contact:
                addContact ();
                break;

            case R.id.item_help:
                help ();
                break;


            case R.id.item_setting:
                Toast.makeText (this, "setting", Toast.LENGTH_SHORT).show ();
                break;
        }
        return super.onOptionsItemSelected (item);
    }


}
