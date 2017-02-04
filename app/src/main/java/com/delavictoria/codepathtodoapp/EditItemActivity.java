package com.delavictoria.codepathtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String value = getIntent().getStringExtra("value");
        position = getIntent().getIntExtra("position", -1);

        EditText editItem = (EditText) findViewById(R.id.etEditItem);
        editItem.requestFocus();
        editItem.setText(value);
        editItem.setSelection(editItem.getText().length());
    }

    public void onEditSave(View view) {
        EditText editItem = (EditText) findViewById(R.id.etEditItem);
        String value =  editItem.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("value", value);
        intent.putExtra("position", position);

        setResult(RESULT_OK, intent);
        this.finish();
    }
}
