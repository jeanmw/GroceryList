package com.example.jeanweatherwax.grocerylist;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryViewHolder> {
  private static final String TAG = GroceryListAdapter.class.getSimpleName();
  private ArrayList<GroceryItem> groceries;
  private Context context;

  public GroceryListAdapter(Context context, ArrayList<GroceryItem> groceries) {
    this.context = context;
    this.groceries = groceries;
  }

  public void setItems(ArrayList<GroceryItem> items) {
    groceries = items;
  }

  @Override
  public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_list_item, parent, false);
    GroceryViewHolder viewHolder = new GroceryViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(GroceryViewHolder holder, int position) {
    GroceryItem groceryItem = groceries.get(position);
    holder.itemNameTextView.setText(groceryItem.getName());

    holder.checkBox.setTag(position);
    holder.checkBox.setChecked(groceryItem.isChecked());

    String format = holder.checkBox.getResources().getString(R.string.item_quantity);
    String quantityText = String.format(format, groceryItem.getQuantity().toString());
    holder.quantityTextView.setText(quantityText);
    holder.itemView.setTag(position);
  }

  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    return groceries.size();
  }

  private View.OnClickListener rowClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle(R.string.item_detail_title);
      int position = (int) view.getTag();
      GroceryItem groceryItem = groceries.get(position);
      String currentItemName = groceryItem.getName();
      String currentDescription = groceryItem.getDescription();
      String currentQuantity = groceryItem.getQuantity().toString();
      String format = context.getString(R.string.item_detail_message);
      String message = String.format(format, currentItemName, currentDescription, currentQuantity);
      builder.setMessage(message);
      builder.create().show();
    }
  };

  private CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
      if (compoundButton.getTag() == null) return;
      int position = (int) compoundButton.getTag();
      GroceryItem groceryItem = groceries.get(position);
      groceryItem.setChecked(b);
      GroceryListPrefs.saveGroceryList(compoundButton.getContext(), groceries);
    }
  };

  public class GroceryViewHolder extends RecyclerView.ViewHolder {
    TextView itemNameTextView;
    TextView quantityTextView;
    CheckBox checkBox;

    public GroceryViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(rowClickListener);
      itemNameTextView = (TextView) itemView.findViewById(R.id.item_name);
      quantityTextView = (TextView) itemView.findViewById(R.id.item_quantity);
      checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
      checkBox.setOnCheckedChangeListener(checkBoxListener);
    }
  }

}
