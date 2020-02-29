package com.sena.invop.Home.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sena.invop.Home.Model.Item;
import com.sena.invop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>  {


    private List<Item> data = new ArrayList<>();
    private Context context ;

    public ItemAdapter( Context context) {
        this.context = context;
    }


    public List<Item> getData() {
        return data;
    }

    public ItemAdapter setData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();
        return this;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_items,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = data.get(position);
        Glide.with(context).load(item.getImg_item()).into(holder.img_item);
        holder.name_inventary.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.last_update.setText(item.getLast_update());

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item)
        ImageView img_item;
        @BindView(R.id.name_product)
        TextView name_inventary;
        @BindView(R.id.last_update)
        TextView last_update;
        @BindView(R.id.description)
        TextView description;



        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}