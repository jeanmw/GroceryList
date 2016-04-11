package com.example.jeanweatherwax.grocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jeanweatherwax on 4/8/16.
 */
public class EditGroceryActivity extends AppCompatActivity {

  private static final String TAG = GroceryListActivity.class.getSimpleName();
  public static final String KEY_PARCELABLE_GROCERIES = "key_parcelable_groceries";

  private ArrayList<GroceryItem> groceries;
  private SimpleBaseAdapter itemAdapter;

  /**
   * Views
   */
  private ListView groceryListView;
  private ImageButton addItemButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_grocery);
    inflateViews();
    initializeAdapter();
    setupAddItemButton();
    setupGroceryListView();
  }

  private void inflateViews() {
    groceryListView = (ListView) findViewById(R.id.groceries_listview);
    addItemButton = (ImageButton) findViewById(R.id.add_button);
  }

  private void initializeAdapter() {
    groceries = this.getIntent().getExtras().getParcelableArrayList(KEY_PARCELABLE_GROCERIES);
    itemAdapter = new SimpleBaseAdapter(this, groceries);
    groceryListView.setAdapter(itemAdapter);
  }

  private void setupAddItemButton() {
    addItemButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        createAddItemDialog();
      }
    });
  }

  private void createAddItemDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(EditGroceryActivity.this);
    builder.setTitle("Add Grocery");
    final View addItemView = LayoutInflater.from(EditGroceryActivity.this).inflate(R.layout.dialog_add_item, null, false);
    builder.setView(addItemView);
    final EditText itemNameEditText = (EditText) addItemView.findViewById(R.id.name);
    final EditText descriptionEditText = (EditText) addItemView.findViewById(R.id.description);
    final EditText quantityEditText = (EditText) addItemView.findViewById(R.id.quantity);
    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        String item = itemNameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        Integer quantity = Integer.valueOf(quantityEditText.getText().toString());
        GroceryItem groceryItem = new GroceryItem(item, description, quantity, false);
        groceries.add(groceryItem);
        GroceryListPrefs.saveGroceryList(EditGroceryActivity.this, groceries);
        itemAdapter.notifyDataSetChanged();
      }
    });
    builder.setNegativeButton("Cancel", null);
    builder.create().show();
  }

  private void setupGroceryListView() {
    groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View v, final int position, long l) {
        createUpdateItemDialog(position);
      }
    });
  }

  private void createUpdateItemDialog(final int position) {
    AlertDialog.Builder builder = new AlertDialog.Builder(EditGroceryActivity.this);

    final GroceryItem groceryItem = itemAdapter.getItem(position);
    builder.setTitle("Edit " + groceryItem.getName());

    View updateItemView = LayoutInflater.from(EditGroceryActivity.this)
            .inflate(R.layout.dialog_update_item, null, false);

    final EditText descriptionEditText = (EditText) updateItemView.findViewById(R.id.description_input);
    descriptionEditText.setText(groceryItem.getDescription());

    final EditText amountEditText = (EditText) updateItemView.findViewById(R.id.quantity_input);
    amountEditText.setText(groceryItem.getQuantity().toString());

    builder.setView(updateItemView);

    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        groceries.remove(position);
        GroceryListPrefs.saveGroceryList(EditGroceryActivity.this, groceries);
        itemAdapter.notifyDataSetChanged();
      }
    });

    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(EditGroceryActivity.this, groceryItem.getName() + " saved", Toast.LENGTH_SHORT).show();
        String description = descriptionEditText.getText().toString();
        Integer quantity = Integer.valueOf(amountEditText.getText().toString());
        groceryItem.setDescription(description);
        groceryItem.setQuantity(quantity);
        groceries.remove(groceryItem);
        groceries.add(groceryItem);
        GroceryListPrefs.saveGroceryList(EditGroceryActivity.this, groceries);
        itemAdapter.notifyDataSetChanged();
      }
    });

    builder.create().show();
  }

}
