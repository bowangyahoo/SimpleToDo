package com.example.bowang.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.joda.time.DateTime;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    private int position = 0;
    private int priority = 1;  // medium
    private long date = -1;
    private Spinner spinner;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String text = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position", 0);
        priority = getIntent().getIntExtra("priority", 1);
        date = getIntent().getLongExtra("date", -1);

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

        spinner.setSelection(priority);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        DateTime dt = new DateTime(date);
        datePicker.updateDate(dt.getYear(),dt.getMonthOfYear()-1,dt.getDayOfMonth());
    }

    public void onEditItem(View v) {
        EditText etItem = (EditText) findViewById(R.id.editText);
        String itemText = etItem.getText().toString();

        Intent i = new Intent(EditItemActivity.this, MainActivity.class);
        i.putExtra("text", itemText); // pass arbitrary data to launched activity
        i.putExtra("position", position);
        i.putExtra("priority", spinner.getSelectedItemPosition());
        i.putExtra("date", getDateFromDatePicker(datePicker).getTime());

        setResult(RESULT_OK, i); // set result code and bundle data for response
        // closes the activity and returns to first screen
        this.finish();
    }

    private static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


}
