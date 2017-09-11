package com.example.nbhung.oderfood;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nbhung.oderfood.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by nbhung on 9/6/2017.
 */

public class signUp extends AppCompatActivity {
    private MaterialEditText edtName, edtPassWord, edtPhone;
    private Button btnSignUp;
    private boolean flat = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPassWord = (MaterialEditText) findViewById(R.id.edtPassWord);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(signUp.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if already User phone
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            if (flat)
                                Toast.makeText(signUp.this, "Phone number already register", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();
                            User users = new User(edtName.getText().toString(), edtPassWord.getText().toString());
                            databaseReference.child(edtPhone.getText().toString()).setValue(users);
                            Toast.makeText(signUp.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                            flat=false;
                            finish();
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
