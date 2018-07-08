package com.example.user.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.user.calendar.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newAppointment(View view){
        Intent intent = new Intent(this, NewAppointmentActivity.class);
        startActivity(intent);
    }

    public void upcomingEvents(View view){
        Intent intent = new Intent(this, UpcomingEventsActivity.class);
        startActivity(intent);
    }

    public void search(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        EditText editText = findViewById(R.id.editText);
        String keyword = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, keyword);
        startActivity(intent);
    }
}
