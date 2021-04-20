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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {
    private TextView title;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> shoppingList = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private View rootView;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        listView = rootView.findViewById(R.id.shopping_list_view);
        arrayAdapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, shoppingList);
        listView.setAdapter(arrayAdapter);

        button = rootView.findViewById(R.id.fragment_shopping_list_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _addItem();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(rootView.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.item_update) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                            View view = LayoutInflater.from(rootView.getContext()).inflate(R.layout.item_dialog, null, false);

                            builder.setTitle("Update Item");
                            EditText editText = view.findViewById(R.id.editText);

                            editText.setText(shoppingList.get(position));

                            builder.setView(view);

                            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!editText.getText().toString().isEmpty()) {
                                        shoppingList.set(position, editText.getText().toString().trim());
                                        arrayAdapter.notifyDataSetChanged();
                                        Toast.makeText(rootView.getContext(), "Item updated!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        editText.setError("Add item here!");
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
                            arrayAdapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                });

                popupMenu.show();
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
        switch(item.getItemId()) {
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
        final EditText editText = view.findViewById(R.id.editText);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!editText.getText().toString().isEmpty()) {
                    shoppingList.add(editText.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();
                    Log.i("MyActivity", shoppingList.get(0));
                } else {
                    editText.setError("Add item here!");
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
