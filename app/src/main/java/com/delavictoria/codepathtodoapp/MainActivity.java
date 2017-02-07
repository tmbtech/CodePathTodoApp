package com.delavictoria.codepathtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.delavictoria.codepathtodoapp.adapters.TodosAdapter;
import com.delavictoria.codepathtodoapp.models.Todo;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TodosAdapter aTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Todo> todos = SQLite
                .select()
                .from(Todo.class)
                .queryList();

        aTodoAdapter = new TodosAdapter(this, todos);

        ListView lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodoAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        aTodoAdapter.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickAdd(View view) {

        EditText etEditText = (EditText) findViewById(R.id.etEditText);
        String value = etEditText.getText().toString();
        etEditText.setText("");

        Todo todo = new Todo();
        todo.setName(value);

        aTodoAdapter.add(todo);
    }

}
