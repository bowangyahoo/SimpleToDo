package com.example.bowang.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EditItemActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    private int position = 0;
    private int priority = 1;  // medium
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String text = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position", 0);

        EditText etItem = (EditText) findViewById(R.id.editText);
        etItem.setText(text);
        etItem.setSelection(text.length());

        spinner = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onEditItem(View v) {
        EditText etItem = (EditText) findViewById(R.id.editText);
        String itemText = etItem.getText().toString();

        Intent i = new Intent(EditItemActivity.this, MainActivity.class);
        i.putExtra("text", itemText); // pass arbitrary data to launched activity
        i.putExtra("position", position);
        i.putExtra("priority", spinner.getSelectedItemPosition());

        setResult(RESULT_OK, i); // set result code and bundle data for response
        // closes the activity and returns to first screen
        this.finish();
    }

}
