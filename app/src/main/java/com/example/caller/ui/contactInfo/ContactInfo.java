package com.example.caller.ui.contactInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.caller.R;
import com.example.caller.models.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactInfo extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_ID = "com.example.caller.ui.contactInfo.extraId";
    public static final String EXTRA_NAME = "com.example.caller.ui.contactInfo.name";
    public static final String EXTRA_PHONE = "com.example.caller.ui.contactInfo.phone";
    EditText editText_name, editText_phone;
    ImageButton imageButton;
    ImageView imageView;
    String name, phone;
    ContactInfoModelView modelView;
    private Bundle extra;
    private FloatingActionButton button;
    private boolean eMode;
    private int mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_contact_info);
        intiView ();
        Intent intent = getIntent ();
        //info&update
        if (intent.hasExtra (EXTRA_ID)) {
            setTitle ("contact info");
            mID = intent.getIntExtra (EXTRA_ID, -1);
            Toast.makeText (this,""+ mID+"", Toast.LENGTH_LONG).show ();
            eMode = true;
            editText_name.setText (intent.getStringExtra (EXTRA_NAME));
            editText_phone.setText (intent.getStringExtra (EXTRA_PHONE));

        }
        //newContact
        else {
            setTitle ("new contact");
            eMode = false;
        }
//        modelView = new ViewModelProvider (this).get (ContactInfoModelView.class);
        modelView = ViewModelProviders.of (ContactInfo.this).get (ContactInfoModelView.class);
        button.setOnClickListener (this);
        imageButton.setOnClickListener (this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.btn_save:
                save ();
                break;
            case R.id.IB_phon:
                makeACall ();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.inf_option_menue, menu);
        return super.onCreateOptionsMenu (menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.item_save:
                save ();
                break;
        }
        return super.onOptionsItemSelected (item);
    }

    private void intiView() {
        editText_name = findViewById (R.id.ET_nam);
        editText_phone = findViewById (R.id.ET_phon);
        imageButton = findViewById (R.id.IB_phon);
        imageView = findViewById (R.id.IV_phot);
        button = findViewById (R.id.btn_save);
    }

    public void makeACall() {
        Toast.makeText (this, "calling" + editText_name.getText () + "....\n" + editText_phone.getText (), Toast.LENGTH_SHORT).show ();
    }

    private void save() {
        Contact contact;
        if (editText_name.getText ().toString ().equals ("") || (editText_name.getText () == null)) {
            Toast.makeText (ContactInfo.this, "put name", Toast.LENGTH_LONG).show ();
            editText_name.requestFocus ();
        } else if (editText_phone.getText ().toString ().equals ("") || (editText_phone.getText () == null)) {
            Toast.makeText (ContactInfo.this, "put phone number", Toast.LENGTH_LONG).show ();
            editText_phone.requestFocus ();
        } else if (eMode) {
            //update
            contact = new Contact (editText_name.getText ().toString (), editText_phone.getText ().toString ());
            contact.setId (mID);
            modelView.update (contact);
            Toast.makeText (this, "contact updated", Toast.LENGTH_SHORT).show ();
            finish ();
        } else {
            //newContact
            name = editText_name.getText ().toString ();
            phone = editText_phone.getText ().toString ();
            modelView.insert (new Contact (name, phone));
            Toast.makeText (this, "contact saved", Toast.LENGTH_SHORT).show ();
            finish ();

        }
    }

    @Override
    protected void onPause() {
        super.onPause ();
        finish ();
    }
}
