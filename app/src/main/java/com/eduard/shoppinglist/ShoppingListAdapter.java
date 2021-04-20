package com.eduard.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListHolder> {
    private ArrayList<ShoppingListItem> shoppingList;
    private Context mContext;
    private static ClickListener clickListener;

    public ShoppingListAdapter(ArrayList<ShoppingListItem> list, Context context) {
        this.shoppingList = list;
        this.mContext = context;
    }

    @Override
    public ShoppingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.shopping_list_item_row, parent, false);
        return new ShoppingListHolder(view);
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        return shoppingList == null? 0: shoppingList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListHolder holder, final int position) {
        final ShoppingListItem shoppingItem = shoppingList.get(position);

        holder.setContactName(shoppingItem.getName());
        holder.setContactNumber(shoppingItem.getQuantity());
        holder.setChecked(shoppingItem.isCompleted());
    }

    public class ShoppingListHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private TextView txtName;
        private TextView txtQuantity;
        private ImageView checked;

        public ShoppingListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtName = itemView.findViewById(R.id.shopping_list_item_name);
            txtQuantity = itemView.findViewById(R.id.shopping_list_item_quantity);
            checked = itemView.findViewById(R.id.item_checked);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        public void setContactName(String name) {
            txtName.setText(name);
        }

        public void setContactNumber(int number) {
            txtQuantity.setText(String.valueOf(number));
        }

        public void setChecked(boolean isCompleted) {
            if (isCompleted) {
                checked.setVisibility(View.VISIBLE);
            } else {
                checked.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ShoppingListAdapter.clickListener = clickListener;
    }
}
