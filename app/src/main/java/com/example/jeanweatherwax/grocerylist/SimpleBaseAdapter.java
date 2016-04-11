package com.example.jeanweatherwax.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeanweatherwax on 4/9/16.
 */
public class SimpleBaseAdapter extends BaseAdapter {

  private ArrayList<GroceryItem> groceries;
  private LayoutInflater inflater;
  boolean[] itemChecked;

  private class ViewHolder {
    TextView itemName;
    TextView quantity;
  }

  public SimpleBaseAdapter(Context context, ArrayList<GroceryItem> groceries) {
    inflater = LayoutInflater.from(context);
    this.groceries = groceries;
  }

  public void setItems(ArrayList<GroceryItem> items) {
    groceries = items;
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
    if(convertView == null) {
      holder = new ViewHolder();
      convertView = inflater.inflate(R.layout.grocery_list_item, null);
      holder.itemName = (TextView) convertView.findViewById(R.id.itemname);
      holder.quantity = (TextView) convertView.findViewById(R.id.itemquantity);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    holder.itemName.setText(groceries.get(position).getName());
    holder.quantity.setText("quantity: " + groceries.get(position).getQuantity().toString());
    return convertView;
  }

}
