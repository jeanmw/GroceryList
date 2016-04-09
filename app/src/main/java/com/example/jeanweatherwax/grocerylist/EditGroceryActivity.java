package com.example.jeanweatherwax.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jeanweatherwax on 4/8/16.
 */
public class EditGroceryActivity extends AppCompatActivity {

  private ListView mGroceryList;
  private ArrayAdapter<String> mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_grocery);

    mGroceryList = (ListView) findViewById(R.id.groceries_listview);

    Intent intent = getIntent();
    ArrayList<String> groceries = intent.getStringArrayListExtra("groceries");


    mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    mGroceryList.setAdapter(mAdapter);


  }


}
