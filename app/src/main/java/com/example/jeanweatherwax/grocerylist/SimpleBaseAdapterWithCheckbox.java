package com.example.jeanweatherwax.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeanweatherwax on 4/10/16.
 */
public class SimpleBaseAdapterWithCheckbox extends BaseAdapter {

  private ArrayList<GroceryItem> groceries;
  private LayoutInflater inflater;

  private class ViewHolder {
    TextView itemName;
    TextView quantity;
    CheckBox checkBox;
  }

  public SimpleBaseAdapterWithCheckbox(Context context, ArrayList<GroceryItem> groceries) {
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
      convertView = inflater.inflate(R.layout.grocery_list_item_with_checkbox, null);
      holder.itemName = (TextView) convertView.findViewById(R.id.itemname);
      holder.quantity = (TextView) convertView.findViewById(R.id.itemquantity);
      holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
      holder.checkBox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View v)
        {
          CheckBox checkBox = (CheckBox) v;
          checkBox.setChecked(true);
        }
      });

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    holder.itemName.setText(groceries.get(position).getName());
    holder.checkBox.setTag(groceries.get(position).getChecked());
    holder.quantity.setText("quantity: " + groceries.get(position).getQuantity().toString());

    return convertView;
  }

}

