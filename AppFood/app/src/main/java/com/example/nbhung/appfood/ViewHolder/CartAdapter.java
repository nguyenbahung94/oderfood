package com.example.nbhung.appfood.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.nbhung.appfood.InterfaceView.ItemClickListener;
import com.example.nbhung.appfood.R;
import com.example.nbhung.appfood.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nbhung on 9/14/2017.
 */
class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvCardName, tvPrice;
    public ImageView imgCartCount;
    private ItemClickListener itemClickListener;

    public void setTvCardName(TextView tvCardName) {
        this.tvCardName = tvCardName;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        tvCardName = (TextView) itemView.findViewById(R.id.cart_item_name);
        tvPrice = (TextView) itemView.findViewById(R.id.cart_item_price);
        imgCartCount = (ImageView) itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + listData.get(position).getQuantity(), Color.RED);
        holder.imgCartCount.setImageDrawable(drawable);
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice())) * (Integer.parseInt(listData.get(position).getQuantity()));
        holder.tvPrice.setText(fmt.format(price));
        holder.tvCardName.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
