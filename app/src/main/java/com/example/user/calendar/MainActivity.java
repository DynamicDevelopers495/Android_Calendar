package com.example.user.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.user.calendar.MESSAGE";
    String dbURL;
    RequestQueue requestQueue;
    TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.textView3);
        Button buttonRefresh = findViewById(R.id.button3);

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }

    public void newAppointment(View view){
        Intent intent = new Intent(this, NewAppointmentActivity.class);
        startActivity(intent);
    }

    /*public void upcomingEvents(View view){
        Intent intent = new Intent(this, UpcomingEventsActivity.class);
        startActivity(intent);
    }*/

    public void search(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        EditText editText = findViewById(R.id.editText);
        String keyword = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, keyword);
        startActivity(intent);
    }

    private void jsonParse(){
        dbURL = "https://honest-eh-63187.herokuapp.com/appointments";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dbURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.toString());
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String title = object.getString("title");
                        String description = object.getString("description");
                        String event_date = object.getString("event_date");
                        mTextViewResult.append(title + " " + description + " " + event_date + "\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
