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

import butterknife.Bind;
import butterknife.ButterKnife;


public class EditGroceryListActivity extends AppCompatActivity {
  private static final String TAG = GroceryListActivity.class.getSimpleName();
  public static final String KEY_PARCELABLE_GROCERIES = "key_parcelable_groceries";

  private ArrayList<GroceryItem> groceries;
  private EditGroceryListAdapter itemAdapter;

  @Bind(R.id.groceries_listview)
  ListView groceryListView;
  @Bind(R.id.add_button)
  ImageButton addItemButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_grocery);
    ButterKnife.bind(this);
    initializeAdapter();
    setupAddItemButton();
    setupGroceryListView();
  }

  private void initializeAdapter() {
    groceries = this.getIntent().getExtras().getParcelableArrayList(KEY_PARCELABLE_GROCERIES);
    itemAdapter = new EditGroceryListAdapter(this, groceries);
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
    AlertDialog.Builder builder = new AlertDialog.Builder(EditGroceryListActivity.this);
    builder.setTitle(R.string.create_item_dialog_title);

    final View addItemView = LayoutInflater.from(EditGroceryListActivity.this).inflate(R.layout.dialog_add_item, null, false);
    final EditText nameEditText = (EditText) addItemView.findViewById(R.id.name);
    final EditText descriptionEditText = (EditText) addItemView.findViewById(R.id.description);
    final EditText quantityEditText = (EditText) addItemView.findViewById(R.id.quantity);
    builder.setView(addItemView);

    builder.setPositiveButton(R.string.create_item_dialog_positive, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        String itemName = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        if (itemName.trim().equals("") || quantityEditText.length() == 0) {
          Toast.makeText(EditGroceryListActivity.this, R.string.standard_dialog_error, Toast.LENGTH_SHORT).show();
          dialogInterface.dismiss();
        } else {
          addItemAndUpdateList(quantityEditText, itemName, description);
        }
      }
    });
    builder.setNegativeButton(R.string.create_item_dialog_negative, null);
    builder.create().show();
  }

  private void addItemAndUpdateList(EditText quantityEditText, String itemName, String description) {
    Integer quantity = Integer.valueOf(quantityEditText.getText().toString());
    GroceryItem groceryItem = new GroceryItem(itemName, description, quantity, false);
    String format = getString(R.string.create_item_dialog_toast_saved);
    String toastMessage = String.format(format, groceryItem.getName());
    Toast.makeText(EditGroceryListActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    groceries.add(groceryItem);
    GroceryListPrefs.saveGroceryList(EditGroceryListActivity.this, groceries);
    itemAdapter.notifyDataSetChanged();
    groceryListView.smoothScrollToPosition(itemAdapter.getCount());
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
    AlertDialog.Builder builder = new AlertDialog.Builder(EditGroceryListActivity.this);
    final GroceryItem groceryItem = itemAdapter.getItem(position);

    String titleFormat = getString(R.string.update_item_dialog_title);
    String title = String.format(titleFormat, groceryItem.getName());
    builder.setTitle(title);

    View updateItemView = LayoutInflater.from(EditGroceryListActivity.this)
            .inflate(R.layout.dialog_update_item, null, false);
    final EditText descriptionEditText = (EditText) updateItemView.findViewById(R.id.description_input);
    descriptionEditText.setText(groceryItem.getDescription());
    final EditText quantityEditText = (EditText) updateItemView.findViewById(R.id.quantity_input);
    quantityEditText.setText(groceryItem.getQuantity().toString());
    builder.setView(updateItemView);

    builder.setNegativeButton(R.string.update_item_dialog_negative, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        groceries.remove(position);
        GroceryListPrefs.saveGroceryList(EditGroceryListActivity.this, groceries);
        itemAdapter.notifyDataSetChanged();
      }
    });

    builder.setPositiveButton(R.string.update_item_dialog_positive, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        String description = descriptionEditText.getText().toString();
        if (quantityEditText.length() == 0 || description.trim().equals("")) {
          Toast.makeText(EditGroceryListActivity.this, R.string.standard_dialog_error, Toast.LENGTH_SHORT).show();
          dialogInterface.dismiss();
        } else {
          String format = getString(R.string.update_item_dialog_toast_saved);
          String toastMessage = String.format(format, groceryItem.getName());
          Toast.makeText(EditGroceryListActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
          Integer quantity = Integer.valueOf(quantityEditText.getText().toString());
          groceryItem.setDescription(description);
          groceryItem.setQuantity(quantity);
          groceries.remove(groceryItem);
          groceries.add(groceryItem);
          GroceryListPrefs.saveGroceryList(EditGroceryListActivity.this, groceries);
          itemAdapter.notifyDataSetChanged();
        }
      }
    });

    builder.create().show();
  }

}
