package com.example.jeanweatherwax.grocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Grocery List Activity: View items, check off items, add new items
 */

public class GroceryListActivity extends AppCompatActivity {
  private static final String TAG = GroceryListActivity.class.getSimpleName();

  @Bind(R.id.recycler_view)
  RecyclerView recyclerView;
  @Bind(R.id.add_button)
  ImageButton addItemButton;
  @Bind(R.id.edit_list)
  Button editListButton;

  private ArrayList<GroceryItem> groceries;
  private GroceryListAdapter itemAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_grocery_list);
    ButterKnife.bind(this);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    groceries = GroceryListPrefs.getGroceryList(GroceryListActivity.this);
    itemAdapter = new GroceryListAdapter(GroceryListActivity.this, groceries);
    recyclerView.setAdapter(itemAdapter);

    addItemButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final AlertDialog.Builder builder = getAddItemBuilder();
        builder.create().show();
      }
    });

    setupEditListButton();

    Toast.makeText(GroceryListActivity.this, R.string.intro_toast, Toast.LENGTH_SHORT).show();
  }

  private AlertDialog.Builder getAddItemBuilder() {
    final AlertDialog.Builder builder = new AlertDialog.Builder(GroceryListActivity.this);
    builder.setTitle(R.string.add_item_dialog_title);

    View addItemView = LayoutInflater.from(GroceryListActivity.this)
            .inflate(R.layout.dialog_add_item, null, false);
    final EditText nameEditText = (EditText) addItemView.findViewById(R.id.name);
    final EditText descriptionEditText = (EditText) addItemView.findViewById(R.id.description);
    final EditText quantityEditText = (EditText) addItemView.findViewById(R.id.quantity);
    builder.setView(addItemView);
    builder.setPositiveButton(R.string.add_item_dialog_positive, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        String itemName = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        if ("".equals(itemName.trim()) || quantityEditText.length() == 0) {
          Toast.makeText(GroceryListActivity.this, R.string.standard_dialog_error, Toast.LENGTH_SHORT).show();
          dialogInterface.dismiss();
        } else {
          addItemAndUpdateList(quantityEditText, itemName, description, nameEditText);
        }
      }
    });
    builder.setNegativeButton(R.string.add_item_dialog_negative, null);
    return builder;
  }

  private void addItemAndUpdateList(EditText quantityEditText, String itemName, String description, EditText nameEditText) {
    Integer quantity = Integer.valueOf(quantityEditText.getText().toString());
    GroceryItem groceryItem = new GroceryItem(itemName, description, quantity, false);
    String format = getString(R.string.add_item_toast_saved);
    String toastMessage = String.format(format, nameEditText.getText().toString());
    Toast.makeText(GroceryListActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    groceries.add(groceryItem);
    GroceryListPrefs.saveGroceryList(GroceryListActivity.this, groceries);
    itemAdapter.notifyDataSetChanged();
    recyclerView.smoothScrollToPosition(itemAdapter.getItemCount());
  }

  private void setupEditListButton() {
    editListButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(GroceryListActivity.this, EditGroceryListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EditGroceryListActivity.KEY_PARCELABLE_GROCERIES, groceries);
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    groceries = GroceryListPrefs.getGroceryList(GroceryListActivity.this);
    itemAdapter.setItems(groceries);
    itemAdapter.notifyDataSetChanged();
  }

}