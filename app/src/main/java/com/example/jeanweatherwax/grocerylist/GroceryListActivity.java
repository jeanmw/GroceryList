package com.example.jeanweatherwax.grocerylist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroceryListActivity extends AppCompatActivity {

  private ListView mGroceryList;
  private ImageButton mAddItem;
  private ArrayAdapter<String> mAdapter;
  private ArrayList<String> groceries;
  final Context context = this;
  public final String itemHint = "item";
  public final String descriptionHint = "description";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_grocery_list);

    mGroceryList = (ListView) findViewById(R.id.groceries_listview);
    mAddItem = (ImageButton) findViewById(R.id.add_button);

    groceries = new ArrayList<String>();


    mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    mGroceryList.setAdapter(mAdapter);

    mAddItem.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Grocery");
        final EditText itemField = new EditText(context);
        itemField.setHint(itemHint);
        final EditText descriptionField = new EditText(context);
        descriptionField.setHint(descriptionHint);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(itemField);
        layout.addView(descriptionField);
        builder.setView(layout);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(context, itemField.getText(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, descriptionField.getText(), Toast.LENGTH_SHORT).show();
            String item = itemField.getText().toString();
            String description = descriptionField.getText().toString();
            mAdapter.add(item);
            groceries.add(item);
            mAdapter.notifyDataSetChanged();
          }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
      }
    });


    mGroceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), GroceryListActivity.class);
        intent.putStringArrayListExtra("groceries", groceries);
        startActivity(intent);
      }
    });




  }
}
