package com.example.nbhung.appfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.example.nbhung.appfood.Database.Database;
import com.example.nbhung.appfood.ViewHolder.CartAdapter;
import com.example.nbhung.appfood.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference request;
    private TextView tvTotalPrice;
    private Button btnPlace;
    private List<Order> cart = new ArrayList<>();
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //Firebase
        database = FirebaseDatabase.getInstance();
        request = database.getReference("Requests");

        //Init
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tvTotalPrice = (TextView) findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlanceOrder);
        loadListFood();
    }

    private void loadListFood() {
        Database database = new Database(this);
        try {
            database.copyDatabase();
            cart=database.getCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);
        //caloulate total price
        int total = 0;
        for (Order order : cart) {
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            Locale locale = new Locale("en", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            tvTotalPrice.setText(fmt.format(total));
        }
    }
}
