package com.erickmxav.doacaodemedicamentos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erickmxav.doacaodemedicamentos.R;
import com.erickmxav.doacaodemedicamentos.adapter.AdapterMedicine;
import com.erickmxav.doacaodemedicamentos.config.FirebaseConfig;
import com.erickmxav.doacaodemedicamentos.helper.Base64Custom;
import com.erickmxav.doacaodemedicamentos.helper.RecyclerItemClickListener;
import com.erickmxav.doacaodemedicamentos.model.Medicine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListMedicinesActivity extends AppCompatActivity {

    private FirebaseAuth authentication = FirebaseConfig.getAuthenticationFirebase();
    private DatabaseReference firebaseRef = FirebaseConfig.getFirebaseDatabase();
    private DatabaseReference userRef;

    private RecyclerView recyclerListMedicines;
    private AdapterMedicine adapter;
    private List<Medicine> medicinesList = new ArrayList<>();
    private Medicine medicine;
    private DatabaseReference medicineRef;
    private ValueEventListener valueEventListenerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_list);

        recyclerListMedicines = findViewById(R.id.recyclerListMedicines);

        //Config adapter
        adapter = new AdapterMedicine (medicinesList, this);

        //Config RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerListMedicines.setLayoutManager(layoutManager);
        recyclerListMedicines.setHasFixedSize(true);
        recyclerListMedicines.setAdapter(adapter);

        //Config eventclick on recyclerview
        recyclerListMedicines.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerListMedicines,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Medicine selectedMedicine = medicinesList.get( position );
                                Intent i = new Intent(getApplicationContext(), MedicinesDescriptionActivity.class);
                                i.putExtra("medicinesDescription", selectedMedicine);
                                startActivity( i );
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }

    public void recoverMedicines(){

        medicineRef = firebaseRef.child("medicines");

        valueEventListenerUser = medicineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                medicinesList.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren() ){

                    Medicine medicine = dados.getValue( Medicine.class );
                    medicinesList.add( medicine );
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recoverMedicines();
    }

    @Override
    protected void onStop() {
        super.onStop();
        medicineRef.removeEventListener( valueEventListenerUser );
    }
}