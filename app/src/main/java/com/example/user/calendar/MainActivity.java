package com.example.user.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.user.calendar.MESSAGE";
    RequestQueue queue;
    String dbURL, title, description, event_date, time, recurring;
    HashMap<String, ArrayList<String>> results;
    TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult = findViewById(R.id.textView3);
        queue = Volley.newRequestQueue(this);
        jsonParse();
    }

    public void newAppointment(View view){
        Intent intent = new Intent(this, NewAppointmentActivity.class);
        startActivity(intent);
    }

    public void upcomingEvents(View view){
        Intent intent = new Intent(this, UpcomingEventsActivity.class);
        String result = "";
        for (String key : results.keySet()){
            ArrayList list = results.get(key);
            for(int i = 0; i <list.size(); i++)
                result += key + " " + list.get(i) + "\n";
        }
        intent.putExtra(EXTRA_MESSAGE, result);
        startActivity(intent);
    }

    public void search(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        EditText editText = findViewById(R.id.editText);
        String keyword = editText.getText().toString().toLowerCase();
        String result = "";
        for(String key : results.keySet()){
            ArrayList<String> searchList = new ArrayList<>();
            ArrayList list = results.get(key);
            key = key.toLowerCase();
            if(key.indexOf(" ") > 0){
                searchList.add(key);
                String subKey = key;
                while(subKey.indexOf(" ") > 0) {
                    searchList.add(subKey.substring(0,key.indexOf(" ")));
                    subKey = subKey.substring(subKey.indexOf(" ") + 1);
                }
                if(subKey.length() != 0)
                    searchList.add(subKey);
            }
            else
                searchList.add(key);
            for(int i = 0; i < list.size(); i++){
                String s = "" + list.get(i);
                String description = s.substring(0, s.indexOf(" ")).toLowerCase();
                if(description.indexOf(" ") > 0){
                    searchList.add(description);
                    while(description.indexOf(" ") > 0) {
                        searchList.add(description.substring(0, description.indexOf(" ")));
                        description = description.substring(description.indexOf(" ") + 1);
                    }
                    if(description.length() != 0)
                        searchList.add(description);
                }
                else
                    searchList.add(description);
                String event_date = s.substring(s.indexOf(" ") + 1, s.lastIndexOf(" ")).toLowerCase();
                if(event_date.indexOf("-") > 0){
                    searchList.add(event_date);
                    while(event_date.indexOf("-") > 0) {
                        searchList.add(event_date.substring(0,event_date.indexOf("-")));
                        event_date = event_date.substring(event_date.indexOf("-") + 1);
                    }
                    if(event_date.length() != 0)
                        searchList.add(event_date);
                }
                else
                    searchList.add(event_date);
                String event_time = s.substring(s.lastIndexOf(" ") + 1).toLowerCase();
                if(event_time.indexOf(":") > 0){
                    searchList.add(event_time);
                    while(event_time.indexOf(":") > 0) {
                        searchList.add(event_time.substring(0,event_time.indexOf(":")));
                        event_time = event_time.substring(event_time.indexOf(":") + 1);
                    }
                    if(event_time.length() != 0)
                        searchList.add(event_time);
                }
                else
                    searchList.add(event_time);
                if(searchList.contains(keyword))
                    result += key + " " + s + "\n";
            }
        }
        intent.putExtra(EXTRA_MESSAGE, result);
        startActivity(intent);
    }

    private void jsonParse(){
        dbURL = "http://arcane-hollows-92806.herokuapp.com/appointments";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dbURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                results = new HashMap<>();
                try {
                    JSONArray appointments = new JSONArray(response);
                    for(int i = 0; i < appointments.length(); i++){
                        JSONObject appointment = (JSONObject) appointments.get(i);
                        title = appointment.getString("title");
                        description = appointment.getString("description");
                        event_date = appointment.getString("event_date");
                        if(event_date.indexOf("T") > 0) {
                            time = event_date.substring(event_date.indexOf("T")+1, event_date.lastIndexOf(":"));
                            event_date = event_date.substring(0, event_date.indexOf("T"));
                        }
                        recurring = appointment.getString("recurring");
                        if(results.containsKey("title"))
                            results.get(title).add(description + " " + event_date + " " + time);
                        else{
                            ArrayList<String> list = new ArrayList<>();
                            list.add(description + " " + event_date + " " + time);
                            results.put(title, list);
                        }
                    }
                } catch (JSONException e) {
                    mTextViewResult.setText("404 NOT FOUND!");
                }
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
