package com.example.jeanweatherwax.grocerylist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroceryListActivity extends AppCompatActivity {

  private ListView mGroceryList;
  private ImageButton mAddItem;
  private Button mEditList;
  private ArrayList<GroceryItem> groceries;
  final Context context = this;
  public final String itemHint = "item";
  public final String descriptionHint = "description";
  public final String amountHint = "quantity";
  public final SimpleBaseAdapter mItemAdapter = new SimpleBaseAdapter(this, groceries);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_grocery_list);

    groceries = new ArrayList<GroceryItem>();

    //Buttons and ListView
    mGroceryList = (ListView) findViewById(R.id.groceries_listview);
    mAddItem = (ImageButton) findViewById(R.id.add_button);
    mEditList = (Button) findViewById(R.id.edit_list);

    //Adapter for Grocery List
    final SimpleBaseAdapter mItemAdapter = new SimpleBaseAdapter(this, groceries);
    mGroceryList.setAdapter(mItemAdapter);

    //add a new item and enter information
    mAddItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Grocery");
        final EditText itemField = new EditText(context);
        itemField.setHint(itemHint);
        final EditText descriptionField = new EditText(context);
        descriptionField.setHint(descriptionHint);
        final EditText amountField = new EditText(context);
        amountField.setInputType(InputType.TYPE_CLASS_NUMBER);
        amountField.setHint(amountHint);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(itemField);
        layout.addView(descriptionField);
        layout.addView(amountField);
        builder.setView(layout);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(context, itemField.getText() + " saved", Toast.LENGTH_SHORT).show();
            String item = itemField.getText().toString();
            String description = descriptionField.getText().toString();
            Integer quantity = Integer.valueOf(amountField.getText().toString());
            GroceryItem groceryItem = new GroceryItem(item, description, quantity);
            groceries.add(groceryItem);
            mItemAdapter.notifyDataSetChanged();
          }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
      }
    });

    //show more information on item click
    mGroceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Item details");
        GroceryItem groceryItem = mItemAdapter.getItem(position);
        String currentItem = groceryItem.getItem();
        String currentDescription = groceryItem.getDescription();
        String currentQuantity = groceryItem.getQuantity().toString();
        builder.setMessage("Item: " + currentItem + "\n" + "description: " + currentDescription + "\n" + "quantity: " + currentQuantity);
        builder.create().show();
      }

    });

    //launch edit list activity
    mEditList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(GroceryListActivity.this, EditGroceryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("groceries", groceries);
        intent.putExtras(bundle);
        System.out.println(groceries);
        startActivity(intent);
      }
    });

  }

  //refresh list after changes
  @Override
  public void onResume()
  {
    super.onResume();

  }

}
