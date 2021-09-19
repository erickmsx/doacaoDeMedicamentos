package com.erickmxav.doacaodemedicamentos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.erickmxav.doacaodemedicamentos.R;
import com.erickmxav.doacaodemedicamentos.adapter.AdapterUserRequest;
import com.erickmxav.doacaodemedicamentos.config.FirebaseConfig;
import com.erickmxav.doacaodemedicamentos.helper.RecyclerItemClickListener;
import com.erickmxav.doacaodemedicamentos.model.UserRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListUserRequestActivity extends AppCompatActivity {

    private FirebaseAuth authentication = FirebaseConfig.getAuthenticationFirebase();
    private DatabaseReference firebaseRef = FirebaseConfig.getFirebaseDatabase();
    private DatabaseReference userRef;
    private DatabaseReference userRequestRef;

    private RecyclerView recyclerListUserRequests;
    private AdapterUserRequest adapter;
    private List<UserRequest> usersRequestList = new ArrayList<>();
    private UserRequest userRequest;
    private ValueEventListener valueEventListenerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_request_list);

        recyclerListUserRequests = findViewById(R.id.recyclerListUserRequests);

        //Config adapter
        adapter = new AdapterUserRequest (usersRequestList, this);

        //Config RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerListUserRequests.setLayoutManager(layoutManager);
        recyclerListUserRequests.setHasFixedSize(true);
        recyclerListUserRequests.setAdapter(adapter);

        //Config eventclick on recyclerview
        recyclerListUserRequests.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerListUserRequests,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                UserRequest selectedUserRequest = usersRequestList.get( position );
                                Intent i = new Intent(getApplicationContext(), UsersRequestDescriptionActivity.class);
                                i.putExtra("usersRequestDescription", selectedUserRequest);
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

    public void recoverUsersRequests(){

        userRequestRef = firebaseRef.child("userRequest");

        valueEventListenerUser = userRequestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                usersRequestList.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren() ){

                    UserRequest userRequest = dados.getValue( UserRequest.class );
                    usersRequestList.add( userRequest );
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
        recoverUsersRequests();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userRequestRef.removeEventListener( valueEventListenerUser );
    }
}