package com.delavictoria.codepathtodoapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.delavictoria.codepathtodoapp.EditItemActivity;
import com.delavictoria.codepathtodoapp.R;
import com.delavictoria.codepathtodoapp.models.Todo;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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

    private void refreshTodos () {
        List<Todo> todos = SQLite
                .select()
                .from(Todo.class)
                .queryList();

        this.todos.clear();
        this.todos.addAll(todos);

        notifyDataSetChanged();
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

        TextView tvPrioirty = (TextView) convertView.findViewById(R.id.tvPriority);
        int priority = todo.getPriority();

        String priorityText = "high";
        int color = R.color.colorAccent;

        if(priority == 1){
            priorityText = "low";
            color = R.color.colorPrimary;
        }else if(priority == 2) {
            priorityText = "medium";
            color = R.color.colorPrimaryDark;
        }

        tvPrioirty.setText(priorityText);
        tvPrioirty.setTextColor(ContextCompat.getColor(context, color));

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
                                refreshTodos();
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
                intent.putExtra("priority", todo.getPriority());
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
            }
        });


        return convertView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int id = data.getExtras().getInt("id", -0);
            String editedValue = data.getExtras().getString("value");
            int priority = data.getExtras().getInt("priority");

            // save to database
            Todo todo = new Todo();
            todo.setId(id);
            todo.setName(editedValue);
            todo.setPriority(priority);
            todo.save();

            // update view
            refreshTodos();
        }
    }
}

