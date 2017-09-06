package com.example.nbhung.oderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnSignUp, btnSignIn;
    private TextView tvSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn = (Button) findViewById(R.id.btnSigIn);
        btnSignUp = (Button) findViewById(R.id.btnSigUp);
        tvSlogan = (TextView) findViewById(R.id.txtSlogan);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singInIntent = new Intent(MainActivity.this, signUp.class);
                startActivity(singInIntent);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singInIntent = new Intent(MainActivity.this, SignIn.class);
                startActivity(singInIntent);
            }
        });
    }
}
