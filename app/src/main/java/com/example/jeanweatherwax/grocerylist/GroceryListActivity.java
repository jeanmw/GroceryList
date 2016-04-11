package com.example.jeanweatherwax.grocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroceryListActivity extends AppCompatActivity {

  private static final String TAG = GroceryListActivity.class.getSimpleName();

  private ListView groceryListView;
  private ImageButton addItemButton;
  private Button editListButton;

  private ArrayList<GroceryItem> groceries;
  private SimpleBaseAdapterWithCheckbox itemAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_grocery_list);


    inflateViews();

    groceries = GroceryListPrefs.getGroceryList(GroceryListActivity.this);

    //Adapter for Grocery List
    itemAdapter = new SimpleBaseAdapterWithCheckbox(GroceryListActivity.this, groceries);
    groceryListView.setAdapter(itemAdapter);

    //add a new item and enter information
    setupAddItemButton();

    Toast.makeText(GroceryListActivity.this, "Add item, check off existing item, or click existing item for more info", Toast.LENGTH_SHORT).show();

    //show more information on item click
    groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GroceryListActivity.this);
        builder.setTitle("Item details");
        GroceryItem groceryItem = itemAdapter.getItem(position);
        String currentItem = groceryItem.getName();
        String currentDescription = groceryItem.getDescription();
        String currentQuantity = groceryItem.getQuantity().toString();
        builder.setMessage("Item: " + currentItem + "\n" + "description: " + currentDescription + "\n" + "quantity: " + currentQuantity);
        builder.create().show();
      }
    });

    setupEditListButton();
  }

  private void inflateViews() {
    groceryListView = (ListView) findViewById(R.id.groceries_listview);
    addItemButton = (ImageButton) findViewById(R.id.add_button);
    editListButton = (Button) findViewById(R.id.edit_list);
  }

  private void setupAddItemButton() {
    addItemButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GroceryListActivity.this);
        builder.setTitle("Add Grocery");

        View addItemView = LayoutInflater.from(GroceryListActivity.this)
                .inflate(R.layout.dialog_add_item, null, false);
        final EditText nameEditText = (EditText) addItemView.findViewById(R.id.name);
        final EditText descriptionEditText = (EditText) addItemView.findViewById(R.id.description);
        final EditText quantityEditText = (EditText) addItemView.findViewById(R.id.quantity);
        builder.setView(addItemView);
        checkEmpty(nameEditText);
        checkEmpty(descriptionEditText);
        checkEmpty(quantityEditText);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            String item = nameEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            Integer quantity = Integer.valueOf(quantityEditText.getText().toString());
            GroceryItem groceryItem = new GroceryItem(item, description, quantity, false);
            Toast.makeText(GroceryListActivity.this, nameEditText.getText() + " saved", Toast.LENGTH_SHORT).show();
            groceries.add(groceryItem);
            GroceryListPrefs.saveGroceryList(GroceryListActivity.this, groceries);
            itemAdapter.notifyDataSetChanged();
          }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
      }
    });
  }

  private void setupEditListButton() {
    editListButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(GroceryListActivity.this, EditGroceryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EditGroceryActivity.KEY_PARCELABLE_GROCERIES, groceries);
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume: ");
    groceries = GroceryListPrefs.getGroceryList(GroceryListActivity.this);
    itemAdapter.setItems(groceries);
    itemAdapter.notifyDataSetChanged();
  }

  public void checkEmpty(EditText editText) {
    String s = editText.getText().toString();
    if(TextUtils.isEmpty(s)) {
      editText.setError("Item cannot be empty");
      return;
    }
  }

}
