package com.delavictoria.codepathtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class EditItemActivity extends AppCompatActivity {
    private int id;
    private int priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String value = getIntent().getStringExtra("value");
        id = getIntent().getIntExtra("id", -1);
        priority = getIntent().getIntExtra("priority", -1);

        addInitialValue(value, priority);
        addListenerOnRatingBar();
    }

    public void addInitialValue(String value, int priority) {
        EditText editItem = (EditText) findViewById(R.id.etEditItem);
        editItem.requestFocus();
        editItem.setText(value);
        editItem.setSelection(editItem.getText().length());

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating((float) priority);
    }

    public void addListenerOnRatingBar() {
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                priority = (int) rating;
            }
        });
    }

    public void onEditSave(View view) {
        EditText editItem = (EditText) findViewById(R.id.etEditItem);
        String value = editItem.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("value", value);
        intent.putExtra("id", id);
        intent.putExtra("priority", priority);

        setResult(RESULT_OK, intent);
        this.finish();
    }
}
