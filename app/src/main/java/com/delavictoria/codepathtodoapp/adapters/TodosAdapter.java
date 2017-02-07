package com.delavictoria.codepathtodoapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.delavictoria.codepathtodoapp.EditItemActivity;
import com.delavictoria.codepathtodoapp.R;
import com.delavictoria.codepathtodoapp.models.Todo;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TodosAdapter extends ArrayAdapter<Todo> {
    private final int REQUEST_CODE = 20;
    private List<Todo> todos;
    private Context context;

    public TodosAdapter(Context context, List<Todo> todos) {
        super(context, 0, todos);
        this.todos = todos;
        this.context = context;
    }

    @Override
    public void add(Todo todo) {
        super.add(todo);
        todo.save();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Todo todo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
        tvItemName.setTag(todo);
        tvItemName.setText(todo.getName());

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                new AlertDialog
                        .Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you would like to delete this item?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                TextView tvName = (TextView) view.findViewById(R.id.tvItemName);
                                Todo todo = (Todo) tvName.getTag();

                                // update database
                                todo.delete();

                                // update view
                                todos.remove(todo.getId());
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

                return true;
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvName = (TextView) view.findViewById(R.id.tvItemName);
                Todo todo = (Todo) tvName.getTag();

                Intent intent = new Intent(context, EditItemActivity.class);
                intent.putExtra("value", todo.getName());
                intent.putExtra("id", todo.getId());
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
            }
        });


        return convertView;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int id = data.getExtras().getInt("id", -0);
            String editedValue = data.getExtras().getString("value");

            // save to database
            Todo todo = new Todo();
            todo.setId(id);
            todo.setName(editedValue);
            todo.save();

            // update view
            todos.set(id, todo);
            notifyDataSetChanged();
        }
    }
}

