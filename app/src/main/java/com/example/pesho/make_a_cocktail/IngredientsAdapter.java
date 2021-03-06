package com.example.pesho.make_a_cocktail;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pesho.make_a_cocktail.model.products.Product;
import com.example.pesho.make_a_cocktail.model.users.UsersManager;

import java.util.ArrayList;

/**
 * Created by Simeon Angelov on 19.9.2016 г..
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>{

    private ArrayList<Product> products;
    Activity activity;
    String loggedUser;
    String tag;

    public IngredientsAdapter(ArrayList<Product> products, Activity activity, String loggedUser) {
        this.products = products;
        this.activity = activity;
        this.loggedUser = loggedUser;
    }

    @Override
    public IngredientsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = activity.getLayoutInflater().inflate(R.layout.adapter_product,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.MyViewHolder holder, int position) {

        final Product p = products.get(position);
        holder.image.setImageResource(p.getImage());
        holder.name.setText(p.getName());

        //edit button
        if(tag == null ){
            holder.button.setImageResource(R.mipmap.shopping_cart);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!(UsersManager.getShoppingList(loggedUser).getProducts().contains(p))) {
                        UsersManager.getShoppingList(loggedUser).addProduct(p);
                        Toast.makeText(activity, "Added in shopping List", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(activity, "Already added", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (tag.equals("Bar Shelf")){
            //remove product from bar shelf and set button to X
            holder.button.setImageResource(R.drawable.cancel);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UsersManager.getBarShelf(loggedUser).getProducts().remove(p);
                    IngredientsAdapter.this.notifyDataSetChanged();
                    Toast.makeText(activity, "Removed", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else if(tag.equals("Shop")) {
            holder.button.setImageResource(R.mipmap.shopping_cart);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!(UsersManager.getShoppingList(loggedUser).getProducts().contains(p))) {
                        UsersManager.getShoppingList(loggedUser).addProduct(p);
                        Toast.makeText(activity, "Added in shopping List", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(activity, "Already added", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(tag.equals("Shopping list")){
            holder.button.setImageResource(R.mipmap.shopping_cart);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!(UsersManager.getBarShelf(loggedUser).getProducts().contains(p))) {
                        UsersManager.getShoppingList(loggedUser).removeProduct(p);
                        UsersManager.getBarShelf(loggedUser).addProduct(p);
                        IngredientsAdapter.this.notifyDataSetChanged();
                        Toast.makeText(activity, "Added in bar shelf", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(activity, "Already added", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setNewList(ArrayList<Product> products) {
        this.products = products;
    }


    public void setTag(String tag) {
        this.tag = tag;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        ImageButton button;

        public MyViewHolder(View row) {
            super(row);
            image = (ImageView) row.findViewById(R.id.ingridientImageView);
            name = (TextView) row.findViewById(R.id.ingridientNameTV);
            button = (ImageButton) row.findViewById(R.id.shoppingCartButton);
        }
    }

    //set adapter and layout manager

}
