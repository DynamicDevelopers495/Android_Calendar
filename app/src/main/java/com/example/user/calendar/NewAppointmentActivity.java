package com.example.user.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewAppointmentActivity extends AppCompatActivity {

    Button add;
    TextView title, description, date, time;
    String dbURL;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        add = findViewById(R.id.button4);
        title = findViewById(R.id.editText9);
        description = findViewById(R.id.editText10);
        date = findViewById(R.id.editText11);
        time = findViewById(R.id.editText12);
        dbURL = "https://honest-eh-63187.herokuapp.com/appointments";

        queue = Volley.newRequestQueue(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, dbURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("title", title.getText().toString());
                        parameters.put("description", description.getText().toString());
                        parameters.put("event_date", date.getText().toString());

                        return parameters;
                    }
                };
                queue.add(request);
            }
        });
    }
}
