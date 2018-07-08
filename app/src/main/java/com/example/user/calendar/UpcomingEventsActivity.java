package com.example.user.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpcomingEventsActivity extends AppCompatActivity {

    Button buttonRefresh;
    TextView mTextViewResult;
    RequestQueue queue;
    String dbURL, result, title, description, event_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);

        Intent intent = getIntent();
        mTextViewResult = findViewById(R.id.textView);
        buttonRefresh = findViewById(R.id.button5);
        String keyword = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mTextViewResult.setText(keyword);

        queue = Volley.newRequestQueue(this);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }

    private void jsonParse(){
        dbURL = "https://honest-eh-63187.herokuapp.com/appointments";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dbURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result = "";
                try {
                    JSONArray appointments = new JSONArray(response);
                    for(int i = 0; i < appointments.length(); i++){
                        JSONObject appointment = (JSONObject) appointments.get(i);
                        title = appointment.getString("title");
                        description = appointment.getString("description");
                        event_date = appointment.getString("event_date");
                        result += title + " " + description + " " + event_date + "\n";
                    }
                } catch (JSONException e) {
                    mTextViewResult.setText("404 NOT FOUND!");
                }
                mTextViewResult.setText(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewResult.setText("404 NOT FOUND!");
            }
        });
        queue.add(stringRequest);
    }
}
