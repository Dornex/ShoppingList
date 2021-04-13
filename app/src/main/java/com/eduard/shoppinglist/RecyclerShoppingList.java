package com.eduard.shoppinglist;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerShoppingList extends AppCompatActivity {
    private ArrayList<ShoppingListItem> shoppingList = new ArrayList<>();
    private RecyclerView recycler;
    private ShoppingListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        recycler = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        adapter = new ShoppingListAdapter(shoppingList, this);
        recycler.setAdapter(adapter);

        shoppingList.add(new ShoppingListItem("Banana", 10, false));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));
        shoppingList.add(new ShoppingListItem("Apple", 5, true));

        adapter.notifyDataSetChanged();
    }
}
