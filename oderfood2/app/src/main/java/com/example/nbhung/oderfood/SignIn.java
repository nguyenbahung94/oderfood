package com.example.nbhung.oderfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nbhung.oderfood.common.common;
import com.example.nbhung.oderfood.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by nbhung on 9/6/2017.
 */

public class SignIn extends AppCompatActivity {
    private EditText edtPhone, edtPassWord;
    private Button btnSigIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPassWord = (EditText) findViewById(R.id.edtPassWord);
        btnSigIn = (Button) findViewById(R.id.btnSigIn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("user");
        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
                progressDialog.setMessage("Please waiting ....");
                progressDialog.show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            User users = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (users.getPassword().equals(edtPassWord.getText().toString())) {
                                progressDialog.dismiss();
                                Toast.makeText(SignIn.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this, Home.class);
                                common.currentUser = users;
                                startActivity(intent);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignIn.this, "Sign in failed", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignIn.this, "User not exits in databse", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
