package com.example.jeanweatherwax.grocerylist;

/**
 * Created by jeanweatherwax on 4/9/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

/** This class is used to make a custom grocery item object */

public class GroceryItem implements Parcelable {

  private String item;
  private String description;
  private Integer quantity;

  public GroceryItem(String item, String description, Integer quantity) {
    this.item = item;
    this.description = description;
    this.quantity = quantity;
  }

  public String getItem() {
    return item;
  }

  public String getDescription() {
    return description;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }




  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.item);
    dest.writeString(this.description);
    dest.writeValue(this.quantity);
  }

  protected GroceryItem(Parcel in) {
    this.item = in.readString();
    this.description = in.readString();
    this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
  }

  public static final Creator<GroceryItem> CREATOR = new Creator<GroceryItem>() {
    @Override
    public GroceryItem createFromParcel(Parcel source) {
      return new GroceryItem(source);
    }

    @Override
    public GroceryItem[] newArray(int size) {
      return new GroceryItem[size];
    }
  };
}
