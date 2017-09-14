package com.example.nbhung.appfood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nbhung.appfood.InterfaceView.ItemClickListener;
import com.example.nbhung.appfood.R;


/**
 * Created by nbhung on 9/6/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtMenuName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);
        txtMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }

    public void SetItemClickListenner(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
          itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
