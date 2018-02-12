package com.kict.pocket;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ipsv2.android.sdk.kict.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneContactList extends AppCompatActivity{

    private Spinner amenities;
    private Spinner office;
    private HashMap<String, String> selection;
    private HashMap<String, String> kict;
    private HashMap<String,String> mahallah;
    private HashMap<String, String> security;
    private HashMap<String, String> domino;
    private HashMap<String, String> health;
    private ArrayList<String> fetch_selection;
    private ArrayList<String> temporary_selection;
    private ArrayAdapter adapter;
    private ArrayAdapter adapter2;
    private String get_selection_value;
    private String get_selection_value2;
    private String reference_number;
    private String reference_second;
    private String get_office_contact;
    private Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contact_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kict phone contact list");
        final ImageButton amenities_icon = (ImageButton)findViewById(R.id.amenities_icon);
        final ImageButton office_icon = (ImageButton)findViewById(R.id.office_icon);
        final Button cancel = (Button)findViewById(R.id.cancel);
        amenities = (Spinner) findViewById(R.id.amenities);
        office = (Spinner) findViewById(R.id.office);
        call = (Button)findViewById(R.id.call_btn);
        fetch_selection = new ArrayList<>();
        temporary_selection = new ArrayList<>();
        selection = new HashMap<String, String>();
        kict = new HashMap<String, String>();
        mahallah = new HashMap<String, String>();
        security = new HashMap<String, String>();
        domino = new HashMap<String, String>();
        health = new HashMap<String, String>();
        storePhoneNumber();
        getSelection();
        call.setEnabled(false);
        call.setBackgroundColor(0xFF777777);

        amenities_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amenities.performClick();
            }
        });

        office_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                office.performClick();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        amenities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                get_selection_value = amenities.getSelectedItem().toString();

                office.setVisibility(View.VISIBLE);
                office.setEnabled(true);
                getData2(get_selection_value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                get_selection_value2 = office.getSelectedItem().toString();
                call.setEnabled(true);
                call.setBackgroundColor(call.getContext().getResources().getColor(R.color.ia_blue));

                switch (reference_second){
                    case "KICT":
                        for (Map.Entry<String, String> entry : kict.entrySet()) {
                            if(entry.getValue() == get_selection_value2){
                                get_office_contact = entry.getKey();
                            }
                        }
                        break;
                    case "MAHALLAH OFFICE":
                        for (Map.Entry<String, String> entry : mahallah.entrySet()) {
                            if(entry.getValue() == get_selection_value2){
                                get_office_contact = entry.getKey();
                            }
                        }
                        break;
                    case "SECURITY":
                        for (Map.Entry<String, String> entry : security.entrySet()) {
                            if(entry.getValue() == get_selection_value2){
                                get_office_contact = entry.getKey();
                            }
                        }
                        break;
                    case "HEALTH AND CLINIC":
                        for (Map.Entry<String, String> entry : health.entrySet()) {
                            if(entry.getValue() == get_selection_value2){
                                get_office_contact = entry.getKey();
                            }
                        }
                        break;
                    case "DOMINOS":
                        for (Map.Entry<String, String> entry : domino.entrySet()) {
                            if(entry.getValue() == get_selection_value2){
                                get_office_contact = entry.getKey();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + get_office_contact));
                    startActivity(callIntent);
                }
            }
        });
    }

    public void getSelection(){

        for (String thing : selection.values()) {
            fetch_selection.add(thing);
        }


        adapter = new ArrayAdapter<String>(PhoneContactList.this, android.R.layout.simple_spinner_dropdown_item, fetch_selection) {

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amenities.setAdapter(adapter);

        amenities.setSelection(adapter.getCount());
    }


    public void getData2(String reference){

        temporary_selection.clear();

        System.out.println(reference);

        switch (reference){
            case "KICT":
                for (String thing : kict.values()) {
                    temporary_selection.add(thing);
                    reference_second = reference;
                }
                break;
            case "MAHALLAH OFFICE":
                for (String thing : mahallah.values()) {
                    temporary_selection.add(thing);
                    reference_second = reference;
                }
                break;
            case "SECURITY":
                for (String thing : security.values()) {
                    temporary_selection.add(thing);
                    reference_second = reference;
                }
                break;
            case "HEALTH AND CLINIC":
                for (String thing : health.values()) {
                    temporary_selection.add(thing);
                    reference_second = reference;
                }
                break;
            case "DOMINOS":
                for (String thing : domino.values()) {
                    temporary_selection.add(thing);
                    reference_second = reference;
                }
                break;
        }

        adapter2 = new ArrayAdapter<String>(PhoneContactList.this, android.R.layout.simple_spinner_dropdown_item, temporary_selection);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        office.setAdapter(adapter2);

    }


    public void storePhoneNumber(){
        selection.put("kict", "KICT");
        selection.put("mahallah", "MAHALLAH OFFICE");
        selection.put("security", "SECURITY");
        selection.put("health","HEALTH AND CLINIC");
        selection.put("domino","DOMINOS");
        selection.put("dummy","Select Amenities");



        kict.put("0361966484","General Office");
        kict.put("0361965601","IS and CS Department");
        mahallah.put("0361964071","Ali");
        mahallah.put("0361964066","Billal");
        mahallah.put("0361964121","Faruq");
        mahallah.put("0361964082","Uthman");
        mahallah.put("0361964061","Siddiq");
        mahallah.put("0361965967","Zubir");
        mahallah.put("0361965981","Salahudin");
        mahallah.put("0361965061","Asiah");
        mahallah.put("0361965496","Aminah");
        mahallah.put("0361964148","Asma");
        mahallah.put("0361964143","Hafsa");
        mahallah.put("0361964077","Halimatul");
        mahallah.put("0361965397","Maryam");
        mahallah.put("0361964125","Nusaibah");
        mahallah.put("0361965974","Roqayah");
        mahallah.put("0361965492","Safiyyah");
        mahallah.put("0361964135","Sumaiyyah");
        security.put("0361965555","0361965555");
        security.put("0361964175","0361964175");
        domino.put("1300888333","1300888333");
        health.put("0361964444","IIUM Health Center");
        health.put("326155555","HKL");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
