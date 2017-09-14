package com.example.nbhung.appfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nbhung.appfood.Database.Database;
import com.example.nbhung.appfood.model.Food;
import com.example.nbhung.appfood.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class foodDetail extends AppCompatActivity {
    private TextView tvFoodName, tvFoodPrice, tvFoodDscription;
    private ImageView foodImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnCart;
    private String foodId = "";
    private FirebaseDatabase database;
    private DatabaseReference foods;
    private Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("foods");

        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(foodDetail.this).addToCart(new Order(foodId, currentFood.getName(),"1000", currentFood.getPrice(), currentFood.getDiscount()));
                Toast.makeText(foodDetail.this, "Added to cart", Toast.LENGTH_LONG).show();
            }
        });

        tvFoodDscription = (TextView) findViewById(R.id.food_description);
        tvFoodName = (TextView) findViewById(R.id.food_name);
        tvFoodPrice = (TextView) findViewById(R.id.food_price);
        foodImage = (ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandeAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);
        Intent intent = getIntent();
        if (intent != null) {
            foodId = intent.getStringExtra("FoodId");
            if (!foodId.isEmpty()) {
                getDetailFood(foodId);
            }
        }
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(foodImage);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                tvFoodPrice.setText(currentFood.getPrice());
                tvFoodName.setText(currentFood.getName());
                tvFoodDscription.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
