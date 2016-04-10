package com.example.jeanweatherwax.grocerylist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jeanweatherwax on 4/8/16.
 */
public class EditGroceryActivity extends AppCompatActivity {

  private ArrayAdapter<String> mAdapter;
  private ListView mGroceryList;
  private ImageButton mAddItem;
  final Context context = this;
  public final String itemHint = "item";
  public final String descriptionHint = "description";
  public final String amountHint = "quantity";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_grocery);

    mGroceryList = (ListView) findViewById(R.id.groceries_listview);
    mAddItem = (ImageButton) findViewById(R.id.add_button);

    final ArrayList<GroceryItem> groceries = this.getIntent().getExtras().getParcelableArrayList("groceries");

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
            Toast.makeText(context, itemField.getText(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, descriptionField.getText(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, amountField.getText(), Toast.LENGTH_SHORT).show();
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

    mGroceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View v, final int position, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final GroceryItem groceryItem = mItemAdapter.getItem(position);
        builder.setTitle("Edit " + groceryItem.getItem());
        final TextView descriptionLabel = new TextView(context);
        descriptionLabel.setText("Description: ");
        final TextView quantityLabel = new TextView(context);
        quantityLabel.setText("Quantity: ");
        final EditText descriptionField = new EditText(context);
        descriptionField.setHint(groceryItem.getDescription());
        final EditText amountField = new EditText(context);
        amountField.setInputType(InputType.TYPE_CLASS_NUMBER);
        amountField.setHint(groceryItem.getQuantity().toString());
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(descriptionLabel);
        layout.addView(descriptionField);
        layout.addView(quantityLabel);
        layout.addView(amountField);
        builder.setView(layout);
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            try {
              groceries.remove(position);
              mItemAdapter.notifyDataSetChanged();
            } catch (NullPointerException e) {
              System.out.print("Null Pointer Exception! empty list");
            }
          }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(context, groceryItem.getItem() + " saved", Toast.LENGTH_SHORT).show();
            String description = descriptionField.getText().toString();
            Integer quantity = Integer.valueOf(amountField.getText().toString());
            groceryItem.setDescription(description);
            groceryItem.setQuantity(quantity);
            mItemAdapter.notifyDataSetChanged();
          }
        });
        builder.create().show();
      }

    });

  }



}
