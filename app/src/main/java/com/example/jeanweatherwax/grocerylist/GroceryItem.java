package com.example.jeanweatherwax.grocerylist;

/**
 * Created by jeanweatherwax on 4/9/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

/** This class is used to make a custom grocery name object */

public class GroceryItem implements Parcelable {
  private String name;
  private String description;
  private Integer quantity;
  private Boolean checked;

  public GroceryItem(String name, String description, Integer quantity, Boolean checked) {
    this.name = name;
    this.description = description;
    this.quantity = quantity;
    this.checked = checked;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public Boolean isChecked() {
    return checked;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public void setChecked(Boolean checked) {
    this.checked = checked;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.description);
    dest.writeValue(this.quantity);
    dest.writeValue(this.checked);
  }

  protected GroceryItem(Parcel in) {
    this.name = in.readString();
    this.description = in.readString();
    this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
    this.checked = (Boolean) in.readValue(Boolean.class.getClassLoader());
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
