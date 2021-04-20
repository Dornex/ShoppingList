package com.eduard.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {
    private TextView title;
    private SharedPreferences sharedPreferences;
    private ArrayList<ShoppingListItem> shoppingList = new ArrayList<>();
    private RecyclerView recyclerView;
    private View rootView;
    private Button button;
    private ShoppingListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        recyclerView = rootView.findViewById(R.id.shopping_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ShoppingListAdapter(shoppingList, getContext());

        adapter.setOnItemClickListener(new ShoppingListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PopupMenu popupMenu = new PopupMenu(rootView.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item_update) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                            View view = LayoutInflater.from(rootView.getContext()).inflate(R.layout.item_dialog, null, false);

                            builder.setTitle("Update Item");
                            EditText editProductName = view.findViewById(R.id.edit_product_name);
                            EditText editProductQuantity = view.findViewById(R.id.edit_product_quantity);

                            editProductName.setText(shoppingList.get(position).getName());
                            editProductQuantity.setText(String.valueOf(shoppingList.get(position).getQuantity()));

                            builder.setView(view);

                            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!editProductName.getText().toString().isEmpty() && !editProductQuantity.getText().toString().isEmpty()) {
                                        shoppingList.get(position).setName(editProductName.getText().toString().trim());
                                        shoppingList.get(position).setQuantity(Integer.parseInt(editProductQuantity.getText().toString().trim()));
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(rootView.getContext(), "Item updated!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        editProductName.setError("Add item here!");
                                    }
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.show();
                        } else {
                            Toast.makeText(rootView.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                            shoppingList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

        recyclerView.setAdapter(adapter);

        button = rootView.findViewById(R.id.fragment_shopping_list_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _addItem();
            }
        });

        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.login_shared_pref), Context.MODE_PRIVATE);

        title = rootView.findViewById(R.id.fragment_shopping_list_title);
        title.setText(String.format("Hello, %s", sharedPreferences.getString(getString(R.string.login_username), "")));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                _addItem();
                break;
        }

        return true;
    }

    private void _addItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
        builder.setTitle("Add new item");

        View view = LayoutInflater.from(rootView.getContext()).inflate(R.layout.item_dialog, null, false);
        builder.setView(view);

        EditText editProductName = view.findViewById(R.id.edit_product_name);
        EditText editProductQuantity = view.findViewById(R.id.edit_product_quantity);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!editProductName.getText().toString().isEmpty() && !editProductQuantity.getText().toString().isEmpty()) {
                    ShoppingListItem listItem = new ShoppingListItem(editProductName.getText().toString().trim(), Integer.parseInt(editProductQuantity.getText().toString().trim()), false);
                    shoppingList.add(listItem);
                    adapter.notifyDataSetChanged();
                } else {
                    editProductName.setError("Add item here!");
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
