package com.example.nbhung.oderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.nbhung.oderfood.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class foodDetail extends AppCompatActivity {
    ElegantNumberButton numberButton;
    private TextView tvFoodName, tvFoodPrice, tvFoodDscription;
    private ImageView foodImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnCart;
    private String foodId = "";
    private FirebaseDatabase database;
    private DatabaseReference foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("foods");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        tvFoodDscription = (TextView) findViewById(R.id.food_description);
        tvFoodName = (TextView) findViewById(R.id.food_name);
        tvFoodPrice = (TextView) findViewById(R.id.food_price);
        foodImage = (ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandeAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);
        Intent intent=getIntent();
        if (intent!= null) {
            foodId =intent.getStringExtra("FoodId");
            if (!foodId.isEmpty()) {
                getDetailFood(foodId);
            }
        }
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(food.getImage()).into(foodImage);
                collapsingToolbarLayout.setTitle(food.getName());
                tvFoodPrice.setText(food.getPrice());
                tvFoodName.setText(food.getName());
                tvFoodDscription.setText(food.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
