package com.eduard.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> shoppingList = new ArrayList<>();
    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, shoppingList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.item_update) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dialog, null, false);

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
                                        Toast.makeText(MainActivity.this, "Item updated!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                            shoppingList.remove(position);
                            arrayAdapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
        Log.i("MyActivity", "Entered Add Item");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add new item");

        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dialog, null, false);
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