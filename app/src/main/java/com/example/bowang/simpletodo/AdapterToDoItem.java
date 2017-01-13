package com.example.bowang.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bowang on 1/12/17.
 */

public class AdapterToDoItem extends ArrayAdapter<ToDoItem> {

    private static final Map<Integer, String> priorityMap = new HashMap<Integer, String>();

    static {
        priorityMap.put(0, "High");
        priorityMap.put(1, "Medium");
        priorityMap.put(2, "Low");
    }

    public AdapterToDoItem(Context context, int simple_list_item_1, ArrayList<ToDoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDoItem item = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.text = (TextView) convertView.findViewById(R.id.txtLabel);
            viewHolder.priority = (TextView) convertView.findViewById(R.id.txtPriority);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.text.setText(item.text);
        viewHolder.priority.setText(priorityMap.get(item.priority));

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView text;
        TextView priority;
    }
}