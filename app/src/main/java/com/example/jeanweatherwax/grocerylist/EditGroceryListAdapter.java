package com.example.jeanweatherwax.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EditGroceryListAdapter extends BaseAdapter {
  private ArrayList<GroceryItem> groceries;
  private LayoutInflater layoutInflater;

  private class ViewHolder {
    TextView itemNameTextView;
    TextView quantityTextView;
  }

  public EditGroceryListAdapter(Context context, ArrayList<GroceryItem> groceries) {
    layoutInflater = LayoutInflater.from(context);
    this.groceries = groceries;
  }

  public int getCount() {
    return groceries.size();
  }

  public GroceryItem getItem(int position) {
    return groceries.get(position);
  }

  public long getItemId(int position) {
    return position;
  }

  public View getView(final int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = layoutInflater.inflate(R.layout.grocery_list_item, null);
      holder.itemNameTextView = (TextView) convertView.findViewById(R.id.item_name);
      holder.quantityTextView = (TextView) convertView.findViewById(R.id.item_quantity);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    holder.itemNameTextView.setText(groceries.get(position).getName());
    String quantityDisplayFormat = convertView.getResources()
            .getString(R.string.item_quantity);
    String quantityDisplay = String.format(quantityDisplayFormat,
            groceries.get(position).getQuantity().toString());
    holder.quantityTextView.setText(quantityDisplay);
    return convertView;
  }

}
