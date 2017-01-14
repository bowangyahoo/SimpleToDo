package com.example.bowang.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    ArrayList<ToDoItem> items;
    AdapterToDoItem itemsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new AdapterToDoItem(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setupListViewListner();
    }

    public void setupListViewListner() {
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.get(position).delete();
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        launchEditView(position);
                    }
                }
        );
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);

        ToDoItem newItem = new ToDoItem();

        newItem.id = items.size() == 0 ? 0 : items.get(items.size() - 1).id + 1; // TODO: resolve potential integer overflow
        newItem.text = etNewItem.getText().toString();
        newItem.priority = 1;
        newItem.date = System.currentTimeMillis();
        newItem.save();

        itemsAdapter.add(newItem);

        etNewItem.setText("");
    }

    private void readItems() {
        items = (ArrayList) SQLite.select().from(ToDoItem.class).queryList();
    }

    private void updateItem(int position, String text, int priority, long date) {
        items.get(position).text = text;
        items.get(position).priority = priority;
        items.get(position).date = date;
        items.get(position).save();
    }

    private void launchEditView(final int position) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);

        i.putExtra("text", items.get(position).text);
        i.putExtra("position", position);
        i.putExtra("priority", items.get(position).priority);
        i.putExtra("date", items.get(position).date);

        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract text value from result extras
            String text = data.getStringExtra("text");
            int position = data.getIntExtra("position", 0);
            int priority = data.getIntExtra("priority", 1);
            long date = data.getLongExtra("date", -1);

            // Toast the name to display temporarily on screen
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            // persist the change to file
            updateItem(position, text, priority, date);
            // notify adapter to reflect the change from UI
            itemsAdapter.notifyDataSetChanged();
        }
    }
}
