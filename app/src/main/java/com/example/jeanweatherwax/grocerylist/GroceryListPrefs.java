package com.example.jeanweatherwax.grocerylist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by jeanweatherwax on 4/10/16.
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
    editor.commit();
  }

  public static ArrayList<GroceryItem> getGroceryList(Context context) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    String serializedGroceryList = prefs.getString(KEY_GROCERY_LIST, "");
    if(serializedGroceryList.equals("")) {
      Log.d(TAG, "getGroceryList: returning empty");
      return new ArrayList<>();
    }
    Gson gson = new Gson();
    Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {}.getType();
    ArrayList<GroceryItem> groceryItems = gson.fromJson(serializedGroceryList, groceryListType);
    return groceryItems;
  }

}
