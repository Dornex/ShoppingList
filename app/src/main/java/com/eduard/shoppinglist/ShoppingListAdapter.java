package com.eduard.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListHolder> {

    // List to store all the contact details
    private ArrayList<ShoppingListItem> shoppingList;
    private Context mContext;
    private AdapterView.OnItemClickListener mListener;

    // Counstructor for the Class
    public ShoppingListAdapter(ArrayList<ShoppingListItem> list, Context context) {
        this.shoppingList = list;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ShoppingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.shopping_list_item_row, parent, false);
        return new ShoppingListHolder(view);
    }

    @Override
    public int getItemCount() {
        return shoppingList == null? 0: shoppingList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ShoppingListHolder holder, final int position) {
        final ShoppingListItem shoppingItem = shoppingList.get(position);

        // Set the data to the views here
        holder.setContactName(shoppingItem.getName());
        holder.setContactNumber(shoppingItem.getQuantity());

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ShoppingListHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtQuantity;

        public ShoppingListHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.shopping_list_item_name);
            txtQuantity = itemView.findViewById(R.id.shopping_list_item_quantity);
        }

        public void setContactName(String name) {
            txtName.setText(name);
        }

        public void setContactNumber(int number) {
            txtQuantity.setText(String.valueOf(number));
        }
    }
}
