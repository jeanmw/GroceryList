package com.example.jeanweatherwax.grocerylist;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * We should ideally serialize/deserialized json on a background thread. For the
 * purposes of a quick demo, I have skipped this as there is no notice-able
 * UI stutter. Ideally, I would use a sqlite database with callbacks instead of
 * SharedPrefs so I know when a write was successfully completed.
 */
public class GroceryListPrefs {
  private static final String TAG = GroceryListPrefs.class.getSimpleName();
  private static final String PREF_FILE_NAME = "groceries_file_name";
  private static final String KEY_GROCERY_LIST = "key_grocery_list";

  public static void saveGroceryList(Context context, ArrayList<GroceryItem> groceryItems) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    Gson gson = new Gson();
    String serializedGroceryList = gson.toJson(groceryItems);
    editor.putString(KEY_GROCERY_LIST, serializedGroceryList);
    editor.apply();
  }

  public static ArrayList<GroceryItem> getGroceryList(Context context) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    String serializedGroceryList = prefs.getString(KEY_GROCERY_LIST, "");
    if (serializedGroceryList.equals("")) {
      return new ArrayList<>();
    }
    Gson gson = new Gson();
    Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {
    }.getType();
    ArrayList<GroceryItem> groceryItems = gson.fromJson(serializedGroceryList, groceryListType);
    return groceryItems;
  }

}
